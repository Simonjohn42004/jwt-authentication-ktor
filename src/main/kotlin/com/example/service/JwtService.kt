package com.example.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import com.example.routing.request.LoginRequest
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.Date

class JwtService(
    private val application: Application,
    private val userService: UserService
) {

    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
    val realm = getConfigProperty("jwt.realm")

    val jwtVerifier:JWTVerifier =
        JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .withAudience(audience)
            .build()

    private fun getConfigProperty(path: String) =
        application.environment.config.property(path).getString()

     fun createJwtToken(loginRequest: LoginRequest): String?{
        val foundUser = userService.findByUserName(loginRequest.userName)

        return if (foundUser != null && foundUser.password == loginRequest.password){
            JWT
                .create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("userName", foundUser.userName)
                .withExpiresAt(Date(System.currentTimeMillis() + 60 * 60 * 60 ))
                .sign(Algorithm.HMAC256(secret))
        }else null
    }

    fun customValidator(credential: JWTCredential): JWTPrincipal?{
        val userName = extractUserName(credential)
        val foundUser = userName?.let { userService.findByUserName(userName) }

        return foundUser?.let {
            if(audienceMatch(credential)){
                JWTPrincipal(credential.payload)
            }else null
        }
    }

    private fun audienceMatch(credential: JWTCredential): Boolean {
        return credential.payload.audience.contains(audience)
    }

    private fun extractUserName(credential: JWTCredential): String? =
        credential.payload.getClaim("userName").asString()
}