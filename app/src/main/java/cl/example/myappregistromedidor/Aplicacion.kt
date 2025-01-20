package cl.example.myappregistromedidor
import android.app.Application
import androidx.room.Room
import cl.example.myappregistromedidor.dao.RegistroDao
import cl.example.myappregistromedidor.db.AppDatabase

class Aplicacion : Application(){
    //crea cada vez que lo va necesitando
    val db by lazy { Room.databaseBuilder( this, AppDatabase::class.java, "registro.db" ).build() }
    val registroDao by lazy{ db.registroDao() }
}