package com.example.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.model.Dessert
import com.example.model.DessertInput
import com.example.services.DessertService
import java.util.*

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {

    inputType<DessertInput> {
        description = "This is the input of the dessert without the schema"
    }
    inputType<Dessert> {
        description = "Desert object to be updated"
    }
    type<Dessert> {
        description = "Desert object with attributes name, description and imageUrl"
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
        resolver {page:Int,size:Int ->
            try {
                dessertService.getDesertsPage(page, size)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createDesert") {
        description = "Create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                dessertService.addDessert(
                    DessertInput(
                        name = dessertInput.name,
                        description = dessertInput.description,
                        imageUrl = dessertInput.imageUrl
                    )
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateDesert") {
        description = "Update a single dessert"
        resolver { dessert: Dessert ->
            try {
                dessertService.updateDessert(dessert)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        description = "Delete a single desert by id"
        resolver { dessertId: String ->
            try {
                dessertService.deleteDesert(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }
}