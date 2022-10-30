package com.example.services

import com.example.models.Profile
import com.example.repository.DessertRepository
import com.example.repository.UsersRepository

class ProfileService {
    val userRepository = UsersRepository()
    val dessertRepository = DessertRepository()

    fun getProfile(userId: String): Profile {
        val user = userRepository.getById(userId)
        val desserts = dessertRepository.getDessertsByUserId(userId)
        return Profile(user = user, desserts = desserts)
    }
}