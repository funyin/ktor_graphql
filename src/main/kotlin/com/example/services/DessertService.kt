package com.example.services

import com.example.models.Dessert
import com.example.models.DessertInput
import com.example.models.DessertsPage
import com.example.repository.DessertRepository
import org.koin.core.component.KoinComponent
import java.util.UUID

class DessertService : KoinComponent {
    private val repository = DessertRepository()

    fun getDesertsPage(page: Int, size: Int): DessertsPage = repository.getDessertsPage(page, size)

    fun getDessert(id: String): Dessert = repository.getById(id)

    fun updateDessert(dessert: Dessert): Dessert {
        return repository.update(entry = dessert)
    }

    fun deleteDesert(id: String): Boolean {
        return repository.delete(id)
    }

    fun addDessert(dessert: DessertInput, userId: String): Dessert {
        return repository.add(
            Dessert(
                _id = UUID.randomUUID().toString(),
                name = dessert.name,
                description = dessert.description,
                imageUrl = dessert.imageUrl,
                userId = userId
            )
        )
    }
}