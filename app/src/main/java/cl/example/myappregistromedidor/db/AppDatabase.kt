package cl.example.myappregistromedidor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.example.myappregistromedidor.dao.RegistroDao
import cl.example.myappregistromedidor.entities.Registro

@Database(entities = [Registro::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao

}