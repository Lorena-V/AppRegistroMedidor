package cl.example.myappregistromedidor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info

import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalGasStation

import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.example.myappregistromedidor.entities.Registro
import cl.example.myappregistromedidor.ui.ListaRegistrosViewModel
import java.time.LocalDate




//import org.w3c.dom.Text
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.lifecycleScope
//import androidx.room.Room
//import cl.example.myappregistromedidor.db.AppDatabase
//import cl.example.myappregistromedidor.entities.Registro
//import cl.example.myappregistromedidor.ui.theme.MyAppRegistroMedidorTheme
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar edge-to-edge si es necesario
        enableEdgeToEdge()

        setContent {
            AppRegistrosUI()
        }
    }
}

@Composable
fun AppRegistrosUI(
    navController: NavHostController = rememberNavController(),
    vmListaRegistros: ListaRegistrosViewModel = viewModel(factory = ListaRegistrosViewModel.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    )
    {
        composable("inicio") {
            PantallaListaRegistros(
                registros = vmListaRegistros.registros,
                onAdd = { navController.navigate("form") }
                )
        }
        composable("form") {
            PantallaFormRegistro(
                vmListaRegistros = vmListaRegistros,
                onRegistroExitoso = { navController.popBackStack()} // Regresa a la pantalla de lista
            )
        }
    }
}

@Composable
fun OpcionesTiposUi( onTipoSeleccionado: (String) -> Unit ){
    val tipos = listOf("Agua", "Luz", "Gas")
    var tipoSeleccionado by rememberSaveable { mutableStateOf(tipos[0]) }
    Column(Modifier.selectableGroup()) {
        tipos.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == tipoSeleccionado),
                        onClick = { tipoSeleccionado = text
                                  onTipoSeleccionado(text)
                                  },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == tipoSeleccionado),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

//@Preview(showSystemUi = true)
@Composable
fun PantallaFormRegistro(
    vmListaRegistros: ListaRegistrosViewModel,
    onRegistroExitoso: () -> Unit // Callback para navegar
) {
    var  medidor by rememberSaveable { mutableIntStateOf(0) }
    var  fecha by rememberSaveable { mutableStateOf("") }
    var tipo by rememberSaveable { mutableStateOf("") } //**

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        // monto fecha descripcion
        // medidor fecha
        TextField(
            value = medidor.toString(),
            onValueChange = { medidor = it.toIntOrNull() ?: 0},
            label = { Text("Medidor") }
        )
        TextField(
            value = fecha,
            onValueChange = { fecha = it},
            placeholder = {Text("2024-01-18")},
            label = { Text("Fecha") }
        )
        Text("Medidor de:")
        OpcionesTiposUi(
            onTipoSeleccionado = { tipoSeleccionado ->
                tipo = tipoSeleccionado
            }
        )

        Button(onClick = {
            // función de ir a la pantalla inicio

            val nuevoRegistro = Registro( null, medidor, LocalDate.now(), tipo)
            vmListaRegistros.insertarRegistro(nuevoRegistro)
            onRegistroExitoso() // Navegar a la pantalla de lista
        }) {
            Text("Registrar Medición")
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PantallaListaRegistros(
    registros: List<Registro> = listOf(),
    onAdd:() -> Unit = {}
){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAdd()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "+")
            }
        }
    ){
        paddingValues ->
        LazyColumn (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            items(registros) {registro ->
                RegistroItem(registro = registro)
                Divider()   // Línea divisoria entre elementos

            }
        }
    }
}

@Composable
fun RegistroItem(registro: Registro) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono representativo del tipo de medición
        val icon = when (registro.tipo) {
            "Agua" -> Icons.Filled.WaterDrop
            "Luz" -> Icons.Filled.Lightbulb
            "Gas" -> Icons.Filled.LocalGasStation
            else -> Icons.Filled.Info
        }

        Icon(
            imageVector = icon,
            contentDescription = registro.tipo,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )

        // Datos del registro
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = registro.tipo,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Medición: ${registro.medidor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Fecha: ${registro.fecha}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
//    // Se ejecuta 1 vez al iniciar el composable
//    LaunchedEffect(Unit) {
//        vmListaRegistros.obtenerRegistros()
//    }

//    LazyColumn {
//        items(vmListaRegistros.registros) {
//            Text(it.tipo)
//        }
//        item {
//            Button(onClick = {
//                vmListaRegistros.insertarRegistro(Registro(null, 15000, LocalDate.now(), "AGUA" ))
//            }) {
//                Text("AGREGAR")
//            }
//        }
//    }
//}


//){
//
//    // se ejecuta 1 vez al iniciar el composable
//    LaunchedEffect(Unit) {
//        vmListaRegistros.obtenerRegistros()
//    }
//
//        item {
//            Button(onClick = {
//                vmListaRegistros.insertarRegistro( Registro(null, 10500, LocalDate.now(), "Agua") )
//            }) {
//                Text("AGREGAR")
//            }
//        }
//    }
//}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyAppRegistroMedidorTheme {
//        Greeting("Android")
//    }
//}