package com.againzahuep.productmanagement.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.againzahuep.productmanagement.data.api.ProductApiAdapter
import com.againzahuep.productmanagement.data.db.ProductDao
import com.againzahuep.productmanagement.data.db.ProductEntity
import com.againzahuep.productmanagement.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApiAdapter: ProductApiAdapter,
    private val productDao: ProductDao
) {
    suspend fun fetchAndSaveInitialProducts() {
        val apiProducts = productApiAdapter.fetchProducts()
        val productEntities = apiProducts.map {
            ProductEntity.fromProduct(it, isReviewed = false)
        }
        productDao.insertProducts(productEntities)
    }

    suspend fun getPendingProducts(): List<Product> {
        return productDao.getPendingProducts().map { it.toProduct() }
    }

    fun getReviewedProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 7,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                productDao.getReviewedProductsPaging()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toProduct() }
        }
    }

    suspend fun reviewProduct(product: Product, isApproved: Boolean) {
        val updatedProduct = product.copy(isApproved = isApproved)
        val productEntity = ProductEntity.fromProduct(updatedProduct, isReviewed = true)
        productDao.updateProduct(productEntity)
    }

    suspend fun deleteProduct(product: Product) {
        val productEntity = ProductEntity.fromProduct(product, isReviewed = true)
        productDao.deleteProduct(productEntity)
    }
}
