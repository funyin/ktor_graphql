package com.example.repository

import com.example.model.Model
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

interface RepositoryInterface<T:Model> {
    val collection: MongoCollection<T>
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

    fun add(entry: T): T{
        return try {
            collection.insertOne(entry)
            entry
        }catch (e:Exception){
            throw Exception("Cannot update Item")
        }
    }
    fun update(entry: T): T{
        return try {
            collection.updateOne(Model::_id eq entry._id,entry)
            collection.findOne(Model::_id eq entry._id)?: throw Exception("No Item with that id exists")
        }catch (e:Exception){
            throw Exception("Cannot update Item")
        }
    }
}