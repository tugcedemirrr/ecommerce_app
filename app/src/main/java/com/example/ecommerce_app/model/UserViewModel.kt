package com.example.ecommerce_app.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class UserViewModel : ViewModel(){
    private val uid = 3
    private val _username = MutableLiveData<String>("") // MutableLiveData wordt gebruikt voor gegevens die kunnen worden gewijzigd en waarvoor waarnemers nodig zijn.
    val username : LiveData<String> = _username // LiveData wordt gebruikt om waarde van _username aan UI door te geven
    private val _password = MutableLiveData<String>("")
    val password : LiveData<String> = _password

    fun setUsername(desiredUser:String){
        _username.value = desiredUser
    }

    fun setPassword(passwordUser:String){
        _password.value = passwordUser
    }

    // Controleren of een user is ingelogd, op basis van de not empty van de username
    fun isLoggedIn() : Boolean{
        return !_username.value.isNullOrEmpty()
    }
}