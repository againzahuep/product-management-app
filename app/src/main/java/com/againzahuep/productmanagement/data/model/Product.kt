package com.againzahuep.productmanagement.data.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String,
    var isApproved: Boolean? = null
)