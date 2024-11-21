package ru.itis.myapplication

open class RecyclerItem(
    val type: Int
)

data class Information(
    val text: String,
    val image: Int,
    val description: String
) : RecyclerItem(type = 0)

object Buttons : RecyclerItem(type = 1)
