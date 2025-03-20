package com.againzahuep.productmanagement.presentation.ui.views.reviewed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.againzahuep.productmanagement.data.model.Product
import com.againzahuep.productmanagement.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewedViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val reviewedProducts: Flow<PagingData<Product>> = repository
        .getReviewedProducts()
        .cachedIn(viewModelScope)

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }
}
