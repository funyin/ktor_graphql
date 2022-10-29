package com.example.services

import com.example.model.Dessert
import com.example.model.DessertInput
import com.example.model.DessertsPage
import com.example.repository.DessertRepository
import com.mongodb.client.MongoClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class DessertService : KoinComponent {
    private val client: MongoClient by inject()
    private val repository = DessertRepository(client)

    fun getDesertsPage(page: Int, size: Int):DessertsPage = repository.getDessertsPage(page, size)

    fun getDessert(id: String): Dessert = repository.getById(id)

    fun updateDessert(dessert: Dessert): Dessert {
        return repository.update(entry = dessert)
    }

    fun deleteDesert(id: String): Boolean {
        return repository.delete(id)
    }

    fun addDessert(dessert: DessertInput): Dessert {
        return repository.add(
            Dessert(
                _id = UUID.randomUUID().toString(),
                name = dessert.name,
                description = dessert.description,
                imageUrl = dessert.imageUrl
            )
        )
    }
}