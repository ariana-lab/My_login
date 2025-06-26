package com.example.my_login

// Importaciones de Jetpack Compose para la UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Columnas, filas, padding, etc.
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.* // Para el manejo de estado
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Importación de Firebase Authentication y Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PerfilScreen(navController: NavController, auth: FirebaseAuth) {
    // Instancia de Firestore
    val db = FirebaseFirestore.getInstance()
    // Obtiene el UID del usuario actualmente autenticado
    val userId = auth.currentUser?.uid

    // Variables de estado para almacenar nombre y correo electrónico
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Efecto lanzado al iniciar pantalla o cambiar el userId
    LaunchedEffect(userId) {
        userId?.let {
            // Consulta Firestore para obtener los datos del usuario
            db.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Actualiza las variables de estado con los datos recuperados
                        nombre = document.getString("nombre") ?: ""
                        email = document.getString("email") ?: ""
                    }
                }
        }
    }

    // Layout principal: columna vertical
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el espacio disponible
            .background( // Fondo con degradado de rosado a blanco
                Brush.verticalGradient(colors = listOf(Color(0xFFDE3163), Color.White))
            )
            .padding(24.dp), // Margen interno
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top // Contenido desde arriba hacia abajo
    ) {
        Spacer(modifier = Modifier.height(40.dp)) // Espaciado superior

        // Imagen del logo (debe estar en drawable)
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Perfil image",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título de bienvenida personalizado con el nombre del usuario
        Text(
            text = "Bienvenido a tu perfil, $nombre",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra el correo electrónico del usuario
        Text(text = "Correo: $email")

        Spacer(modifier = Modifier.height(32.dp))

        // Modifier común para todos los botones del perfil
        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)

        // Botón: "Cuenta"
        Button(
            onClick = { /* Aquí puedes mostrar más info de la cuenta */ },
            modifier = buttonModifier,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDE3163), // Fondo rosado
                contentColor = Color.White // Texto blanco
            )
        ) {
            Text("Cuenta")
        }

        // Otros botones del perfil usando un composable reutilizable
        ProfileButton(text = "Información personal", modifier = buttonModifier)
        ProfileButton(text = "Mis estadísticas", modifier = buttonModifier)
        ProfileButton(text = "Configuraciones", modifier = buttonModifier)
        ProfileButton(text = "Información de la app", modifier = buttonModifier)

        Spacer(modifier = Modifier.height(32.dp))

        // Botón: "Cerrar sesión"
        Button(
            onClick = {
                auth.signOut() // Cierra sesión de Firebase
                navController.navigate(Routes.loginscreen) { // Navega a la pantalla de login
                    popUpTo(0) // Elimina el historial de navegación
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Fondo blanco
                contentColor = Color.Black // Texto negro
            )
        ) {
            Text("Cerrar sesión")
        }
    }
}

// ✅ Composable reutilizable para crear botones del perfil
@Composable
fun ProfileButton(text: String, modifier: Modifier) {
    Button(
        onClick = { /* Aquí puedes definir la acción de cada botón */ },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFDE3163), // Fondo rosado
            contentColor = Color.White // Texto blanco
        )
    ) {
        Text(text)
    }
}
