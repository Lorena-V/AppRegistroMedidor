package cl.example.myappregistromedidor.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Registro (
    @PrimaryKey(autoGenerate = true ) val id:Int,
    val medidor: Int,
    val fecha: String,
    val tipo: String
)