package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.*
import com.example.services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {

    inputType<DessertInput> {
        description = "This is the input of the dessert without the schema"
    }
    inputType<MDessert> {
        description = "Desert object to be updated"
    }
    type<Dessert> {
        description = "Desert object with attributes name, description and imageUrl"
    }
    type<DessertsPage> {
        description = "The a p page of desserts"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                dessertService.getDessert(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("desserts") {
        resolver { page: Int?, size: Int? ->
            try {
                dessertService.getDesertsPage(page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createDesert") {
        description = "Create a new dessert"
        resolver { context: Context, dessertInput: DessertInput ->
            try {
                val userId = context.get<User>()?._id ?: error("Not signed in")
                dessertService.addDessert(dessertInput, userId = userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateDesert") {
        description = "Update a single dessert"
        resolver { context: Context, dessert: MDessert ->
            try {
                val userId = context.get<User>()?._id ?: error("Not signed in")
                dessertService.updateDessert(dessert.toDessert().copy(userId = userId))
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        description = "Delete a single desert by id"
        resolver { context: Context, dessertId: String ->
            try {
                context.get<User>()?._id ?: error("Not signed in")
                dessertService.deleteDesert(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }
}