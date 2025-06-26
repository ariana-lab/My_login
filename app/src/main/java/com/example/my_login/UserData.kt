package com.example.my_login

// Objeto Singleton en Kotlin que guarda los datos del usuario en memoria
object UserData {
    // Variable para almacenar el nombre del usuario
    var nombre: String = ""

    // Variable para almacenar el email del usuario
    var email: String = ""

    // Variable para almacenar la contraseña del usuario (⚠️ NO recomendado guardar contraseñas así en apps reales)
    var contraseña: String = ""
}
