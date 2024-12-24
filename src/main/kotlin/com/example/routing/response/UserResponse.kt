package com.example.routing.response

import com.example.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID
import javax.print.attribute.standard.RequestingUserName

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val userName: String
)