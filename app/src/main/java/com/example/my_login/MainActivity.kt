package com.example.my_login

import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val user = auth.currentUser
            NavHost(
                navController = navController,
                startDestination = if (user != null) Routes.homescreen else Routes.loginscreen
            ) {
                composable(Routes.loginscreen) {
                    LoginScreen(navController, auth)
                }
                composable(Routes.registerscreen) {
                    RegisterScreen(navController, auth)
                }
                composable(Routes.homescreen) {
                    HomeScreen(navController, auth)
                }
                composable(Routes.perfilscreen) {
                    PerfilScreen(navController, auth)
                }
                composable(Routes.stadisticscreen) {
                    StatisticScreen()
                }

            }
        }
    }
}


