package cl.example.myappregistromedidor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cl.example.myappregistromedidor.entities.Registro

@Dao
    interface RegistroDao {

    @Query("SELECT * FROM Registro ORDER BY fecha DESC")
    suspend fun obtenerTodos(): List<Registro>

    //@Query("SELECT * FROM Registro WHERE id = :id")
    //suspend fun (id:Int): Registro

    @Insert
    suspend fun insertar(registro:Registro )

    @Update
    suspend fun modificar(registro: Registro)

    //@Delete
    //fun delete(registro: Registro)
}