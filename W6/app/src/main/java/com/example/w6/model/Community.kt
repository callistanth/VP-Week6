package com.example.w6.model

data class Community(
    val id: String,
    val name: String,
    val fullName: String,
    val description: String,
    val memberCount: Int,
    val isJoined: Boolean,
    val iconKey: String = "code"
)
