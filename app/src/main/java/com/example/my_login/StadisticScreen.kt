package com.example.my_login


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.my_login.ui.theme.awa



@Composable
fun Usuario() {
    //row nos sirve para poder poner cosas al lado de una como el logo y el nombre
    Row(
        modifier = Modifier
            .fillMaxWidth() //le decimos que ocupe todo el ancho disponible
            .padding(16.dp), //se agrega como espacio alrededor de ella
        verticalAlignment = Alignment.CenterVertically // centramos verticalmente , cualquier elemento
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Imagen de perfil",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(12.dp)) //le damos un espaciado entre la imagen y el usuario de ejemplo
        Column {
            //dejamos el ambos textos arriba de otro usando column
            Text(
                text = "Usuario Ejemplo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Perfil 1",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun Informacion_awa() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center  //dejar al centro las cosas
    ) {
        Grafico_awa( progress = 0.68f) //llamamos al grafico_awa que está abajo el dibujo con canva

        Spacer(modifier = Modifier.width(12.dp)) //le damos un espaciado

        Column{
            Text("68%", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Agua consumida", style = MaterialTheme.typography.bodyMedium)
        }




    }
}

@Composable
//
fun Grafico_awa(progress: Float) {
    Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center) {
        //dentro de la caja modificamos el tamaño del grafico lo dejamos en 50.dp y lo alineamos al centro
        Box(
            modifier = Modifier
                .fillMaxSize()
                //ocupa todo el tamaño de los 50dp que le dimos
                .background(awa.copy(alpha = 0.15f), CircleShape)
            //circleshape es para que sea la caja redonda.
            //aqui llamamos a awa que es el color en Color.kt y le decimos que sea mas opaco con 0.15f ,
        )
        //y aqui es donde dibujamos
        // le damos el color que empieze desde el -90 grafos
        Canvas(modifier = Modifier.size(60.dp)) {
            drawArc(
                color = awa,
                startAngle = -90f,
                sweepAngle = 360 * progress, //le damos un progreso en este caso se lo damos en la informacion arriba
                //pero igualmente lo multiplicamos por el total de los grados de el circulo
                useCenter = false,
                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}




@Composable
fun Lista_pasos(color: Color, text: String) {

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}


// al importar drawarc para hacer el circulo del grafico , es una libreria interna de jetpack compose
//val es una variable que no cambia
@Composable
fun GraficoCircular(
    modificador: Modifier,
    valor: List<Double>, //por ejempo en la data tenemos listof(2.0, 3.0,1.0)
    // me sirve para sumar el total de los pasos , caminar , correr ,etc.
    colores: List<Color>,
    espacioenGrados: Int =5, //espacio en blanco en gradoos , para dejar en cada pedazo de el circulo
    lineaGrosor: Float = 80f //grosor y f es float decimal
) {
    val totalValor = valor.sum() //aqui sumamos la lista values
    val gradosDisponibles = 360f - (espacioenGrados * valor.size) //esto es para dar separacion entre las partes
    //un circulo tiene 360 grados , habria que restarle la separacion
    // values.size es lo que esta adentro (2.0,1.0,3.0) en este caso hay 3
    //por ende se dibuja solo 320° del circulo ya que se restan 4 que 4 son las actividades


    // un box para meter todo el dibujo y los textos adentro.

    Box(modifier = modificador, contentAlignment = Alignment.Center) {
        //alignment.center para que todo este alineado al centro en este caso el grafico
        Canvas(modifier = Modifier.matchParentSize()) {
            //matchparentsize() es para que el grafico use todo el espacio disponible dentro de la caja
            val estilo_linea = Stroke(width = lineaGrosor, cap = StrokeCap.Round)
            //aqui le damos que el estilo linea sea el grosor y al final del extremo sea redondeado
            var empezarDibujarCirculo = -90f //

            for (i in valor.indices) {
                //recorremos el valor de la lista [2.0 3.0 1.0)
                // para hacerle el calculo abajo
                val calculo = (valor[i] / totalValor * gradosDisponibles).toFloat()
                //aqui hacemos el calculo de cuantos grados ocupará en el circulo
                //drawarc nos sirve para poder dibujar cada parte del circulo , con el color correspondiente a cada parte , que empieze con el angulo, y su calculo
                drawArc(
                    color = colores[i],
                    startAngle = empezarDibujarCirculo,
                    sweepAngle = calculo,
                    useCenter = false,
                    style = estilo_linea
                )
                //y esto es basicamente para que cuando empieze de nuevo a dibujar el circulo no se sobreponga
                // es para que el nuevo pedazo no se vea encima de los demas
                empezarDibujarCirculo += calculo + espacioenGrados
            }
        }

        //aqui mostramos el texto al centro de la dona con el numero total.

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //columna ya que todo se va a mostrar de arriba hacia abajo
            //y centramos horizontalmente
            Text(
                text = "${totalValor}k", //insertamos una variable val dentro de un texto
                style = MaterialTheme.typography.headlineMedium, //tamaño de letra
                fontWeight = FontWeight.Bold //aqui le damos que la fuente sea en negrita
            )
            Text(
                text = "Pasos totales",
                style = MaterialTheme.typography.bodyMedium, //le letra tamaño
            )
        }
    }
}



@Composable
fun Inicio() {
    var mesActual by remember { mutableStateOf(mesesData.first()) }
    //aqui guardamos el mesactual que se está mostrando
    //mutablestateof es un estado observable
    //variable var que es mutable osea puede cambiar
    //y recordamos osea Quiero empezar mostrando el primer mes de la lista mesesData
    //osea que recuerde en que mes está la pestañana cuando se va cambiando.


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), //margen con los bordes de la pantalla
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Tarjeta de usuario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Usuario()
            }
        }

        // Tarjeta de agua
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Informacion_awa()
            }
        }

        // Tarjeta con gráfico circular y lista de pasos
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val currentIndex = mesesData.indexOf(mesActual)
                //currentindex guarda LA POSICION del el mes que se está mostrando en pantalla
                //indexof es lo que nos devuelve la posicion

                // Navegación de meses
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                    //con spacebetween decimos que el iconbotoon este uno a la derecha y otro a la isquierda
                ) {
                    IconButton(onClick = {
                        if (currentIndex > 0) {
                            //esto es para que el usuario empiece en el primer mes y para no buscar como el indice -1
                            mesActual = mesesData[currentIndex - 1] //logica para retroseder de mes
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Mes anterior")
                        //utilizamos un icono de flecha para volver atras con arroback con material design
                    }

                    Text(mesActual.mesesNombre, fontWeight = FontWeight.Bold)

                    IconButton(onClick = {
                        if (currentIndex < mesesData.size - 1) {
                            mesActual = mesesData[currentIndex + 1]
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Mes siguiente")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Gráfico circular
                GraficoCircular(
                    modificador = Modifier.size(180.dp),
                    valor = mesActual.actividadesMes, //AQUI LE PASAMOS EL OBJETO MESACTUAL , ES PARA QUE SEPA CADA ACTIVIDAD POR MES
                    colores = Actividades.map { it.colores } //it colores para que solo nos pase los colores de las actividades
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de pasos
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    for (i in Actividades.indices) {
                        Lista_pasos(
                            color = Actividades[i].colores,
                            text = "${Actividades[i].nombre} ${mesActual.actividadesMes[i]}k"
                        )
                    }
                }
            }
        }
    }
}




