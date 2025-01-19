package cl.example.myappregistromedidor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cl.example.myappregistromedidor.entities.Registro

@Dao
interface RegistroDao {

    @Query("SELECT * FROM registro")
    fun getAll(): List<Registro>

    @Insert
    fun insertAll(registro:Registro )

    @Delete
    fun delete(registro: Registro)
}