package com.example.routing

import com.example.model.User
import com.example.routing.request.UserRequest
import com.example.routing.response.UserResponse
import com.example.service.UserService
import io.ktor.http.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userService: UserService) {
    post {
        val userRequest = call.receive<UserRequest>()

        val user = userService.saveUser(
            user = userRequest.toModel()
        )

        if (user == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        call.response.header(
            name = "id",
            value = user.id.toString()
        )

        call.respond(HttpStatusCode.Created)
    }

    authenticate("Authentication") {

        get {
            val users = userService.findAll()

            call.respond(users.map {
                it.toResponse()
            })
        }

        get("/{id}") {
            val idByText = call.parameters["id"]
            if (idByText == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val userFound = userService.findById(idByText)
            if (userFound == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            if (userFound.userName != extractPrincipalUserName(call)){
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

                call.respond(userFound.toResponse())
        }
    }

}

private fun RoutingContext.extractPrincipalUserName(call: RoutingCall): String? {
    return call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("userName")
        ?.asString()
}

private fun UserRequest.toModel(): User {
    return User(
        id = UUID.randomUUID(),
        userName = this.userName,
        password = this.password
    )
}

private fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id,
        userName = userName
    )
}