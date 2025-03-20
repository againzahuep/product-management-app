package com.againzahuep.productmanagement.data.db

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE isReviewed = 0")
    suspend fun getPendingProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE isReviewed = 1")
    fun getReviewedProductsPaging(): PagingSource<Int, ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT COUNT(*) FROM products WHERE isReviewed = 1")
    suspend fun getReviewedProductCount(): Int
}