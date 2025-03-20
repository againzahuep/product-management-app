package com.againzahuep.productmanagement.data.api

import com.againzahuep.productmanagement.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(@Query("_limit") limit: Int = 10): List<Product>
}