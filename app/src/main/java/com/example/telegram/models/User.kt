package com.example.telegram.models

/*  Модель User */

data class User(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullname: String = "",
    var state: String = "",
    var photoUrl: String = "empty",
    var phone: String = ""
)