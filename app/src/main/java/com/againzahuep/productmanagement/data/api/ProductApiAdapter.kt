package com.againzahuep.productmanagement.data.api

import com.againzahuep.productmanagement.data.model.Product
import javax.inject.Inject

class ProductApiAdapter @Inject constructor(
    private val apiService: ProductApiService
) {
    suspend fun fetchProducts(limit: Int = 10): List<Product> {
        return apiService.getProducts(limit)
    }
}