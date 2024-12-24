package com.example.plugins

import com.example.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(
    jwtService: JwtService
) {
    authentication {
        jwt("Authentication") {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)

            validate {
                jwtService.customValidator(it)
            }
        }
    }
}