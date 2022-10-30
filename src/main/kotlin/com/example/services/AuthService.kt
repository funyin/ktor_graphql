package com.example.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.User
import com.example.models.UserInput
import com.example.models.UserResponse
import com.example.repository.UsersRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.koin.core.component.KoinComponent
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.util.UUID

class AuthService : KoinComponent {
    private val userRepository = UsersRepository()
    private val secret = "algoSecret98@38+sd2989s!,2"
    private val algorithm = Algorithm.HMAC256(secret)
    private val verifier = JWT.require(algorithm).build()
    fun signIn(userInput: UserInput): UserResponse? {
        val user = userRepository.getUserByEmail(email = userInput.email) ?: error("No such user by that email")
        if (BCrypt.verifyer()
                .verify(userInput.password.toByteArray(Charsets.UTF_8), user.hashedPass)
                .verified
        ) {
            val token = signAccessToken(user._id)
            return UserResponse(token, user)
        }
        error("Password Incorrect")
    }

    private fun signAccessToken(_id: String): String {
        return JWT.create()
            .withIssuer("example")
            .withClaim("userId", _id)
            .sign(algorithm)
    }

    fun signUp(userInput: UserInput): UserResponse {
        val hashedPassword = BCrypt.withDefaults()
            .hash(10, userInput.password.toByteArray(StandardCharsets.UTF_8))
        val id = UUID.randomUUID().toString()
        val emailUser = userRepository.getUserByEmail(userInput.email)
        if (emailUser != null)
            error("Email already in use")
        val newUser = userRepository.add(
            User(
                _id = id,
                email = userInput.email,
                hashedPass = hashedPassword
            )
        )

        val token = signAccessToken(newUser._id)
        return UserResponse(token, newUser)
    }

    fun verifyAuthToken(call: ApplicationCall): User? {
        return try {
            val authHeader = call.request.header(HttpHeaders.Authorization)
            val authToken = authHeader?.split("Bearer ")?.last()
            val accessToken = verifier.verify(JWT.decode(authToken))
            val userId = accessToken.getClaim("userId").asString()
            return User(_id = userId, email = "", hashedPass = ByteArray(0))
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}