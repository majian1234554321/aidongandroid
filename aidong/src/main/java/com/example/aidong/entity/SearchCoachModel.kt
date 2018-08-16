package com.example.aidong.entity


data class SearchCoachModel(
    val status: Int,
    val data: Data
)

data class Data(
    val user: List<User>
)

data class User(
    val id: Any,
    val wx_no: String,
    val name: String
)