package com.example.plugins

import com.apurebase.kgraphql.GraphQL
import com.example.graphql.dessertSchema
import com.example.services.DessertService
import io.ktor.server.application.*

fun Application.configureGraphql() {
    install(GraphQL) {
        playground = true
        val dessertService = DessertService()
        schema {
            dessertSchema(dessertService)
        }
    }
}