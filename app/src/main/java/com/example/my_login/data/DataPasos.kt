package com.example.my_login



import androidx.compose.ui.graphics.Color
import com.example.my_login.ui.theme.*

//data class nos sirve para guardar los datos.
//ademas que vamos a usar muchas actividad_data en el otro archivo.
data class Actividad_Data(
    val nombre: String,
    val colores: Color
)

data class MesesActividadData(
    val mesesNombre: String,
    val actividadesMes: List<Double>
)

val Actividades = listOf(
    Actividad_Data("Caminar", CaminarColor),
    Actividad_Data("Carrera", CarreraColor),
    Actividad_Data("Bicicleta", BicicletaColor),
    Actividad_Data("Nadar", NadarColor)
)

val mesesData = listOf(
    MesesActividadData("Enero", listOf(3.0,0.5,3.3,4.0)),
    MesesActividadData("Febrero", listOf(2.0,0.5,2.3,4.0)),
    MesesActividadData("Marzo", listOf(6.0,0.2,2.3,5.0)),
    MesesActividadData("Abril", listOf(2.0,1.1,3.3,1.0)),
    MesesActividadData("Mayo",  listOf(2.1, 1.0, 1.9, 3.7)),
    MesesActividadData("Junio",  listOf(3.0, 1.5, 2.0, 1.0)),
    MesesActividadData("Julio",  listOf(1.5, 0.8, 1.0, 2.0)),
    MesesActividadData("Agosto",  listOf(4.0, 2.0, 3.0, 3.0)),
    MesesActividadData("Septiembre",  listOf(2.5, 1.2, 1.5, 6.0)),
    MesesActividadData("Octubre",  listOf(3.0, 3.0, 4.2, 2.0)),
    MesesActividadData("Noviembre",  listOf(1.0, 1.0, 3.2, 1.0)),
    MesesActividadData("Diciembre",  listOf(2.0, 4.0, 1.2, 1.0))

)