package com.example.repository

import com.example.models.User
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import kotlin.Exception

class UsersRepository : Repository<User>(collectionName = "users", collectionType = User::class.java) {
    fun getUserByEmail(email: String? = null): User? {
        return try {
            collection.findOne(User::email eq email)
        } catch (e: Exception) {
            throw Exception("Cannot find user with email")
        }
    }
}