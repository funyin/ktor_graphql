package com.example.repository

import com.example.models.Dessert
import com.example.models.DessertsPage
import com.example.models.PagingInfo
import org.litote.kmongo.eq

class DessertRepository : Repository<Dessert>(
    collectionName = Dessert::class.java.simpleName,
    collectionType = Dessert::class.java
) {

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

    fun getDessertsByUserId(userId: String): List<Dessert> {
        return try {
            collection.find(Dessert::userId eq userId).toList()
        } catch (e: Exception) {
            throw Exception("Cannot get user Desserts")
        }
    }
}