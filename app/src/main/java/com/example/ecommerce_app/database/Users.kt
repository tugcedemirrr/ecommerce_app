package com.example.ecommerce_app.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "password") val password: String?,
)

@Dao
interface UsersDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getUsers(): Users

    // Gebruikt om in te loggen op basis van de opgegeven gebruikersnaam en wachtwoord
    @Query("SELECT * FROM users WHERE username = :Username AND password = :Password LIMIT 1")
    fun logIn(Username: String, Password: String): Users

    // Gebruikt om te controleren of een gebruiker met de opgegeven gebruikersnaam bestaat
    @Query("SELECT COUNT(*) > 0 FROM users WHERE username = :Username")
    fun userExists(Username: String): Boolean

    @Insert
    fun insert(users: Users)

    @Delete
    fun delete(users: Users)
}