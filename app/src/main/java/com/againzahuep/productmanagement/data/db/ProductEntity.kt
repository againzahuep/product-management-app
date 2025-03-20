package com.againzahuep.productmanagement.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.againzahuep.productmanagement.data.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String,
    val isApproved: Boolean?,
    val isReviewed: Boolean
) {
    fun toProduct(): Product {
        return Product(
            id = id,
            title = title,
            description = description,
            price = price,
            category = category,
            imageUrl = imageUrl,
            isApproved = isApproved
        )
    }

    companion object {
        fun fromProduct(product: Product, isReviewed: Boolean): ProductEntity {
            return ProductEntity(
                id = product.id,
                title = product.title,
                description = product.description,
                price = product.price,
                category = product.category,
                imageUrl = product.imageUrl,
                isApproved = product.isApproved,
                isReviewed = isReviewed
            )
        }
    }
}