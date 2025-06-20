package com.example.my_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PerfilScreen(navController: NavController, auth: FirebaseAuth) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFDE3163), Color.White)
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Perfil image",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bienvenido a tu perfil",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)

        ProfileButton(text = "Cuenta", modifier = buttonModifier)
        ProfileButton(text = "Información personal", modifier = buttonModifier)
        ProfileButton(text = "Mis estadísticas", modifier = buttonModifier)
        ProfileButton(text = "Configuraciones", modifier = buttonModifier)
        ProfileButton(text = "Información de la app", modifier = buttonModifier)

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {
                auth.signOut()
                navController.navigate(Routes.loginscreen) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
fun ProfileButton(text: String, modifier: Modifier) {
    Button(
        onClick = {},
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFDE3163),
            contentColor = Color.White
        )
    ) {
        Text(text)
    }
}


