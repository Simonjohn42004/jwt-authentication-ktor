package com.example.routing

import com.example.routing.request.LoginRequest
import com.example.service.JwtService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.authRoute(jwtService: JwtService){

    post {
        val loadingRequest = call.receive<LoginRequest>()

        val token = jwtService.createJwtToken(loadingRequest)

        token?.let {
            call.respond(HttpStatusCode.OK,hashMapOf("token" to it))
        }?: call.respond(HttpStatusCode.Unauthorized)

    }

}