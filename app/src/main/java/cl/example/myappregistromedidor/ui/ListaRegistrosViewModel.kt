package cl.example.myappregistromedidor.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.example.myappregistromedidor.Aplicacion
import cl.example.myappregistromedidor.dao.RegistroDao
import cl.example.myappregistromedidor.entities.Registro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaRegistrosViewModel( private val registroDao: RegistroDao): ViewModel() {

    var registros by mutableStateOf(listOf<Registro>())
    // Corrutina para insertar registros
    fun insertarRegistro(registro: Registro) {
        viewModelScope.launch(Dispatchers.IO) {
            registroDao.insertar(registro)
            obtenerRegistros()
        }
    }

    // Corrutina para obtener registros
    fun obtenerRegistros(): List<Registro> {
        viewModelScope.launch(Dispatchers.IO) {
            registros = registroDao.obtenerTodos()
        }
        return registros
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                ListaRegistrosViewModel(aplicacion.registroDao)
            }
        }
    }
}