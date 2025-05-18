package com.example.my_login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
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
import androidx.navigation.Navigator


@Composable
fun LoginScreen(navController: NavController){

    var email by remember {
        mutableStateOf("")
    }

    var contraseña by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(
            colors = listOf(
                Color(0xFFDE3163),
                Color(0xFFFFFFFF))
            )
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = "Login image", modifier = Modifier.size(200.dp))
        Text(text ="Equilibrio+", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Ingresa a tu cuenta!")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
            email = it },
            label = {Text(text = "Email...")},
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFFFFF),
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle( color = Color.Black, fontSize = 16.sp
            ))
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contraseña,
            onValueChange = {
            contraseña = it
        },label = {
            Text(text = "Contraseña...") },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFFFFFF),
                unfocusedBorderColor = Color.White
            ),
            textStyle = TextStyle( color = Color.Black, fontSize = 16.sp),
            visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                Log.i("Credential", "Usuario: $email Contraseña:$contraseña")
                navController.navigate(Routes.homescreen)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF),
                contentColor = Color.Black
            )
        )
        {
            Text(text = "Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDE3163),
                contentColor = Color.White
            )
        )
        {
            Text(text = "Registrarse")
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = { }) {
            Text(text = "¿Has olvidado tu contraseña?")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Puedes ingresar con")

        Row (
            modifier = Modifier.fillMaxWidth().padding(40.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly

            ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google",
                modifier = Modifier.size(60.dp).clickable {
                    //Google clicked
                    } )

            Image(
                painter = painterResource(id = R.drawable.ic_blackx),
                contentDescription = "Twitter",
                modifier = Modifier.size(60.dp).clickable {
                    //Twitter clicked
                })

            Image(
                painter = painterResource(id = R.drawable.ic_fac),
                contentDescription = "Facebook",
                modifier = Modifier.size(60.dp).clickable {
                    //Facebook clicked
                })
        }

    }

}