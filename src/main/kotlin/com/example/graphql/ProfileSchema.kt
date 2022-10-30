package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.Profile
import com.example.models.User
import com.example.services.ProfileService
import java.lang.Exception

fun SchemaBuilder.profileSchema(service: ProfileService) {

    type<Profile> {
        description = "A users profile data"
    }
    query("getProfile") {
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                service.getProfile(userId)
            } catch (e: Exception) {
                null
            }
        }
    }
}