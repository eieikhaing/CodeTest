package com.hana.codetest.models.post

data class UserRequest(
    val userId: String,
    val id: Int,
    val title: String,
    val body: String
)