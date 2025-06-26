package com.example.my_login

// Importaciones necesarias de Jetpack Compose para la UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.* // Para variables de estado (state)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Importaciones de Firebase Authentication y Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavController, auth: FirebaseAuth) {
    // Instancia de Firestore
    val db = FirebaseFirestore.getInstance()

    // Variables de estado para almacenar los campos de registro
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) } // Para mensajes de error

    // Layout principal: columna vertical
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .background( // Fondo con degradado vertical (rosado a blanco)
                Brush.verticalGradient(colors = listOf(Color(0xFFDE3163), Color.White))
            )
            .padding(horizontal = 24.dp), // Padding lateral
        verticalArrangement = Arrangement.Center, // Centra verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
    ) {
        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Register image",
            modifier = Modifier.size(200.dp)
        )

        // Título
        Text(text = "Equilibrio+", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Crea tu cuenta!")

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto: Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto: Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto: Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            visualTransformation = PasswordVisualTransformation(), // Oculta el texto
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto: Repetir Contraseña
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repetir contraseña...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de registro
        Button(onClick = {
            // Validación de contraseñas
            if (password != repeatPassword) {
                error = "Las contraseñas no coinciden"
            } else if (password.length < 6) {
                error = "La contraseña debe tener al menos 6 caracteres"
            } else {
                // Crear usuario en Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            val userId = auth.currentUser?.uid // Obtener el UID del usuario

                            // Crear el perfil del usuario para Firestore
                            val userProfile = hashMapOf(
                                "nombre" to nombre,
                                "email" to email
                            )

                            // Guardar en la colección "users"
                            userId?.let {
                                db.collection("users").document(it)
                                    .set(userProfile)
                                    .addOnSuccessListener {
                                        // Navegar a la pantalla principal después del registro
                                        navController.navigate(Routes.homescreen) {
                                            popUpTo("register") { inclusive = true }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        error = "Error guardando perfil: ${e.localizedMessage}"
                                    }
                            }
                        } else {
                            // Si falla el registro en Firebase
                            error = "Error de registro: ${result.exception?.localizedMessage}"
                        }
                    }
            }
        }) {
            Text("Registrarse")
        }

        // Mostrar mensaje de error si existe
        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
