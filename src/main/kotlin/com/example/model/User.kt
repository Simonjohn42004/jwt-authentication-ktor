package com.example.model

import com.example.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(

    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val userName: String,
    val password: String
)