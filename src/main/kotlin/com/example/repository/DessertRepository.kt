package com.example.repository

import com.example.model.Dessert
import com.example.model.DessertsPage
import com.example.model.PagingInfo
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.getCollection

class DessertRepository(client: MongoClient) : RepositoryInterface<Dessert> {
    override val collection: MongoCollection<Dessert>

    init {
        val database = client.getDatabase("test")
        collection = database.getCollection<Dessert>(Dessert::class.java.simpleName)
    }

    fun getDessertsPage(page: Int, size: Int): DessertsPage {
        return try {
            val skips = page * size
            val response = collection.find().skip(skips).limit(size)
            val results = response.toList()
            val totalDesserts = collection.estimatedDocumentCount()
            val totalPages: Int = (totalDesserts / size).toInt() + 1
            val nextPage = if (results.size == size) page + 1 else null
            val previousPage = if (page != 0) page - 1 else null
            val pagingInfo = PagingInfo(
                count = totalDesserts.toInt(),
                pages = totalPages,
                next = nextPage,
                previous = previousPage
            )
            DessertsPage(
                results = results,
                info = pagingInfo
            )
        } catch (e: Exception) {
            throw Exception("Cannot get desserts page")
        }
    }
}