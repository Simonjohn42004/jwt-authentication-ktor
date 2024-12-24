package com.example.routing

import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService
){
    routing {

        route("/api/user"){
            userRoute(userService)
        }
    }
}