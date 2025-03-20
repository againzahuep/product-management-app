package com.againzahuep.productmanagement.di


import android.content.Context
import com.againzahuep.productmanagement.data.api.ProductApiAdapter
import com.againzahuep.productmanagement.data.api.ProductApiService
import com.againzahuep.productmanagement.data.db.ProductDao
import com.againzahuep.productmanagement.data.db.ProductDatabase
import com.againzahuep.productmanagement.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://67dc716ce00db03c40681aac.mockapi.io/v1/products")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApiAdapter(apiService: ProductApiService): ProductApiAdapter {
        return ProductApiAdapter(apiService)
    }

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return ProductDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductDatabase) = database.productDao()

    @Provides
    @Singleton
    fun provideProductRepository(
        productApiAdapter: ProductApiAdapter,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepository(productApiAdapter, productDao)
    }
}
