package com.example.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val userName: String,
    val password: String
)