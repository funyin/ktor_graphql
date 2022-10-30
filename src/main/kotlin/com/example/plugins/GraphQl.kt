package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.authSchema
import com.example.graphql.dessertSchema
import com.example.graphql.profileSchema
import com.example.services.AuthService
import com.example.services.DessertService
import com.example.services.ProfileService
import io.ktor.server.application.*

fun Application.configureGraphql() {
    install(GraphQL) {
        playground = true
        val dessertService = DessertService()
        val authService = AuthService()
        val profileService = ProfileService()
        context { call ->
            authService.verifyAuthToken(call)?.let { +it }
            +log
            +call
        }
        schema {
            dessertSchema(dessertService)
            authSchema(authService)
            profileSchema(profileService)
        }
    }
}