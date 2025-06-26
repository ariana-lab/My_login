package com.example.my_login

// Importación de componentes UI de Jetpack Compose
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // Para columnas, filas, espaciados, etc.
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.* // Para manejo de estados (state)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController

// Importación de Firebase Authentication y Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {

    // Variables de estado para los campos de texto y posibles errores
    var email by remember { mutableStateOf("") } // Estado para el email
    var contraseña by remember { mutableStateOf("") } // Estado para la contraseña
    var error by remember { mutableStateOf<String?>(null) } // Estado para mensajes de error

    // Layout principal en columna
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .background(
                Brush.verticalGradient( // Fondo degradado de rosado a blanco
                    colors = listOf(
                        Color(0xFFDE3163),
                        Color(0xFFFFFFFF)
                    )
                )
            ),
        verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
    ) {
        // Imagen de logo
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Login image",
            modifier = Modifier.size(200.dp)
        )

        // Título de la app
        Text(text = "Equilibrio+", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(6.dp)) // Espaciado vertical
        Text(text = "Ingresa a tu cuenta!")

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado

        // Campo de texto para el Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la Contraseña
        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text(text = "Contraseña...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            visualTransformation = PasswordVisualTransformation() // Oculta caracteres
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de inicio de sesión
        Button(
            onClick = {
                // Lógica de inicio de sesión con Firebase Authentication
                auth.signInWithEmailAndPassword(email, contraseña)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            val userId = auth.currentUser?.uid // Obtener UID del usuario

                            val db = FirebaseFirestore.getInstance() // Instancia de Firestore

                            userId?.let { uid ->
                                db.collection("users").document(uid).get()
                                    .addOnSuccessListener { document ->
                                        if (document != null && document.exists()) {
                                            // Si el documento del usuario existe, guarda nombre y email
                                            UserData.nombre = document.getString("nombre") ?: ""
                                            UserData.email = email

                                            // Navega a la pantalla principal (homescreen)
                                            navController.navigate(Routes.homescreen) {
                                                popUpTo("login") { inclusive = true } // Quita el login del back stack
                                            }
                                        } else {
                                            error = "No se encontró perfil asociado."
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        error = "Error cargando perfil: ${e.localizedMessage}"
                                    }
                            }
                        } else {
                            error = "Cuenta no encontrada.\n¡Regístrate para guardar tus estadísticas!"
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Color de fondo del botón
                contentColor = Color.Black // Color del texto del botón
            )
        ) {
            Text(text = "Iniciar sesión")
        }

        // Mostrar mensaje de error si existe
        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para navegar a la pantalla de registro
        Button(
            onClick = {
                navController.navigate(Routes.registerscreen)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDE3163),
                contentColor = Color.White
            )
        ) {
            Text(text = "Registrarse")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Texto clicable para recuperación de contraseña (aún sin implementar)
        TextButton(onClick = { }) {
            Text(text = "¿Has olvidado tu contraseña?")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Texto adicional
        Text(text = "Puedes ingresar con")

        // Iconos de redes sociales (solo visuales, sin lógica implementada)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            // Icono Google
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { /* Aquí iría el código para login con Google */ }
            )

            // Icono Twitter (X)
            Image(
                painter = painterResource(id = R.drawable.ic_blackx),
                contentDescription = "Twitter",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { /* Aquí iría el código para login con Twitter */ }
            )

            // Icono Facebook
            Image(
                painter = painterResource(id = R.drawable.ic_fac),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { /* Aquí iría el código para login con Facebook */ }
            )
        }
    }
}
