package com.example.models

data class Dessert(
    override val _id: String, var name: String,
    var description: String, var userId: String, var imageUrl: String
) : Model

data class MDessert(
    val _id: String, var name: String,
    var description: String, var userId: String, var imageUrl: String
) {
    fun toDessert() = Dessert(_id = _id, name = name, description = description, userId = userId, imageUrl = imageUrl)
}

data class DessertInput(val name: String, val description: String, val imageUrl: String)

data class PagingInfo(val count: Int, val pages: Int, val next: Int?, var previous: Int?)

data class DessertsPage(val results: List<Dessert>, val info: PagingInfo)