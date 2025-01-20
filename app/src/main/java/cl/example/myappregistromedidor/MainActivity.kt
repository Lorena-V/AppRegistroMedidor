package cl.example.myappregistromedidor

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.example.myappregistromedidor.entities.Registro
import cl.example.myappregistromedidor.ui.ListaRegistrosViewModel
import androidx.compose.material3.Text
import org.w3c.dom.Text
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
    vmListaRegistros: ListaRegistrosViewModel = viewModel(factory = ListaRegistrosViewModel.Factory)
) {
    // Se ejecuta 1 vez al iniciar el composable
    LaunchedEffect(Unit) {
        vmListaRegistros.obtenerRegistros()
    }

    LazyColumn {
        items(vmListaRegistros.registros) {
            Text(it.tipo)
        }
        item {
            Button(onClick = {
                vmListaRegistros.insertarRegistro(Registro(null, 15000, LocalDate.now(), "AGUA" ))
            }) {
                Text("AGREGAR")
            }
        }
    }
}


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


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyAppRegistroMedidorTheme {
//        Greeting("Android")
//    }
//}