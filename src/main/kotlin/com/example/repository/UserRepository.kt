package com.example.repository

import com.example.model.User
import java.util.UUID

class UserRepository{

    private val users = mutableListOf<User>()

    fun findAll(): List<User> = users

    fun findById(id: UUID) : User? =
        users.find {
            it.id == id
        }

    fun findByUserName(userName: String): User? =
        users.find{
            it.userName == userName
        }

    fun saveUser(user: User): Boolean =
        users.add(user)
}