
package com.example.my_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.my_login.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class ActivityType(
    val displayName: String,
    val color: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    CORRER("Correr", CarreraColor, Icons.Default.DirectionsRun),
    SALTAR("Saltar", Pink80, Icons.Default.WavingHand),
    CAMINAR("Caminar", CaminarColor, Icons.Default.DirectionsWalk),
    BICICLETA("Bicicleta", BicicletaColor, Icons.Default.DirectionsBike),
    NADAR("Nadar", NadarColor, Icons.Default.Pool)
}

@Composable
fun StatisticScreen() {
    var dailyTotals by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var username by remember { mutableStateOf("Perfil") }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser
    val userId = user?.uid ?: ""
    val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

    LaunchedEffect(Unit) {
        username = user?.email?.substringBefore("@") ?: "Perfil"
        if (userId.isBlank()) return@LaunchedEffect

        // Consulta Firestore para obtener el nombre del usuario
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    username = document.getString("nombre") ?: "Perfil"
                }
            }

        db.collection("user_activities").document(userId)
            .collection("daily_data").document(today)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val data = mutableMapOf<String, Int>()
                    ActivityType.values().forEach { activity ->
                        val key = activity.name.lowercase()
                        data[key] = document.getLong(key)?.toInt() ?: 0
                    }
                    dailyTotals = data
                }
            }
    }

    fun registrarPasos(activity: ActivityType) {
        if (userId.isBlank()) return
        val pasosGenerados = (100..500).random()
        val key = activity.name.lowercase()
        val nuevosTotales = dailyTotals.toMutableMap()
        nuevosTotales[key] = (nuevosTotales[key] ?: 0) + pasosGenerados
        dailyTotals = nuevosTotales

        db.collection("user_activities").document(userId)
            .collection("daily_data").document(today)
            .set(nuevosTotales)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Usuario(username)
        }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Pasos de Hoy", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                val chartData = remember(dailyTotals) {
                    dailyTotals.map { (key, value) ->
                        val activity = ActivityType.values().first { it.name.lowercase() == key }
                        DonutChartData(
                            value = value.toFloat(),
                            color = activity.color
                        )
                    }
                }

                Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                    DonutChart(
                        modifier = Modifier.size(200.dp),
                        chartData = chartData
                    )
                    Text(
                        text = "${dailyTotals.values.sum()} Pasos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(16.dp))

                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    dailyTotals.forEach { (key, value) ->
                        if (value > 0) {
                            val activity = ActivityType.values().first { it.name.lowercase() == key }
                            Lista_pasos(color = activity.color, text = "${activity.displayName}: $value Pasos")
                        }
                    }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Presiona una actividad para registrar pasos", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    ActivityType.values().forEach { activity ->
                        IconButton(
                            onClick = { registrarPasos(activity) },
                            modifier = Modifier
                                .size(56.dp)
                                .background(activity.color.copy(alpha = 0.1f), CircleShape)
                        ) {
                            Icon(activity.icon, activity.displayName, tint = activity.color)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Usuario(username: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painterResource(R.drawable.ic_logo), "Imagen de perfil", modifier = Modifier.size(40.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(username, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Estadisticas", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun Lista_pasos(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape)); Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}