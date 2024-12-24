package com.example.service

import com.example.model.User
import com.example.repository.UserRepository
import java.util.*

class UserService(
    private val userRepository: UserRepository
) {
    fun findAll(): List<User> =
        userRepository.findAll()

    fun findById(id: String): User? =
        userRepository.findById(id = UUID.fromString(id))

    fun findByUserName(userName: String): User? =
        userRepository.findByUserName(userName)

    fun saveUser(user: User): User? {
        val foundUser = findByUserName(user.userName)

        return if (foundUser == null) {
            userRepository.saveUser(user)
            user
        } else {
            null
        }
    }
}