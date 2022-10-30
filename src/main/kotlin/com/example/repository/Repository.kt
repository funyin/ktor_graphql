package com.example.repository

import com.example.models.Model
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

abstract class Repository<T : Model>(
    databaseName: String = "test",
    collectionName: String,
    collectionType: Class<T>
) : KoinComponent {
    val collection: MongoCollection<T>

    init {
        val client: MongoClient by inject()
        val database = client.getDatabase(databaseName)
        collection = database.getCollection(collectionName, collectionType)
    }

    fun getById(id: String): T {
        return try {
            collection.findOne(Model::_id eq id) ?: throw Exception("No item with that id exists")
        } catch (e: Exception) {
            throw Exception("Can't find Id")
        }
    }

    fun getAll(): List<T> {
        return try {
            val res = collection.find()
            res.toList()
        } catch (e: Exception) {
            throw java.lang.Exception("Cannot get all items")
        }
    }

    fun delete(id: String): Boolean {
        return try {
            collection.findOneAndDelete(Model::_id eq id) ?: throw Exception("No item with that id exists")
            true
        } catch (e: Exception) {
            throw Exception("Cannot delete item")
        }
    }

    fun add(entry: T): T {
        return try {
            collection.insertOne(entry)
            entry
        } catch (e: Exception) {
            throw Exception("Cannot add Item")
        }
    }

    fun update(entry: T): T {
        return try {
            collection.updateOne(Model::_id eq entry._id, entry)
            collection.findOne(Model::_id eq entry._id) ?: throw Exception("No Item with that id exists")
        } catch (e: Exception) {
            throw Exception("Cannot update Item")
        }
    }
}