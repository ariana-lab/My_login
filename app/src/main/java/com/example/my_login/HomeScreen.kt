package com.example.my_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavController, auth: FirebaseAuth){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Barra superior
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconPerfil(navController)
            IconMenu(navController, auth)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(150.dp)
                .border(3.dp, Color.Black, shape = RoundedCornerShape(15.dp))
                .clickable { navController.navigate(Routes.stadisticscreen) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_stadistics),
                contentDescription = "Gráfica",
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cuadrícula 2x2
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonAgua()
                BotonTerminal()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonEjercicio()
                BotonSueno()
            }
        }

        Spacer(modifier = Modifier.weight(2f))

        // Botones inferiores
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotonConfiguracion()
            BotonDashboard(navController)
            BotonReloj()
        }
    }
}

@Composable
fun IconPerfil(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "Perfil",
        modifier = Modifier
            .size(32.dp)
            .clickable {
                navController.navigate(Routes.perfilscreen)
            }
    )
}

@Composable
fun IconMenu(navController: NavController, auth: FirebaseAuth) {
    Image(
        painter = painterResource(id = R.drawable.ic_menu),
        contentDescription = "Menú",
        modifier = Modifier
            .size(32.dp)
            .clickable {
                // Cerrar sesión y navegar al login
                auth.signOut()
                navController.navigate(Routes.loginscreen) {
                    popUpTo(0) { inclusive = true } // Limpia el backstack
                }
            }
    )
}

@Composable
fun BotonAgua() {
    BoxButton(imageRes = R.drawable.ic_water, description = "Agua") {
        println("Botón Agua presionado")
    }
}

@Composable
fun BotonTerminal() {
    BoxButton(imageRes = R.drawable.ic_code, description = "Terminal") {
        println("Botón Terminal presionado")
    }
}

@Composable
fun BotonEjercicio() {
    BoxButton(imageRes = R.drawable.ic_sport, description = "Ejercicio") {
        println("Botón Ejercicio presionado")
    }
}

@Composable
fun BotonSueno() {
    BoxButton(imageRes = R.drawable.ic_stres, description = "Sueño") {
        println("Botón Sueño presionado")
    }
}

@Composable
fun BotonConfiguracion() {
    BottomButton(imageRes = R.drawable.ic_settings, description = "Configuración") {
        println("Botón Configuración presionado")
    }
}

@Composable
fun BotonDashboard(navController: NavController) {
    BottomButton(imageRes = R.drawable.ic_wiget, description = "Dashboard")
    {
        navController.navigate(Routes.homescreen)
    }
}

@Composable
fun BotonReloj() {
    BottomButton(imageRes = R.drawable.ic_watch, description = "Reloj") {
        println("Botón Reloj presionado")
    }
}
@Composable
fun BoxButton(imageRes: Int, description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(Color.White)
            .border(3.dp, Color.Black, shape = RoundedCornerShape(15.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = description,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun BottomButton(imageRes: Int, description: String, onClick: () -> Unit,) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .background(Color.White)
            .border(3.dp, Color.Black, shape = RoundedCornerShape(15.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = description,
            modifier = Modifier.size(28.dp)
        )
    }
}
