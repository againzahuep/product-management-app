package com.againzahuep.productmanagement.presentation.ui.views.pendingreviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.againzahuep.productmanagement.data.model.Product
import com.againzahuep.productmanagement.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingReviewViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _pendingProducts = MutableLiveData<List<Product>>()
    val pendingProducts: LiveData<List<Product>> = _pendingProducts

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        fetchPendingProducts()
    }

    private fun fetchPendingProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Intentar obtener productos pendientes de la base de datos
                var products = repository.getPendingProducts()

                // Si no hay productos en la base de datos, obtenerlos de la API
                if (products.isEmpty()) {
                    repository.fetchAndSaveInitialProducts()
                    products = repository.getPendingProducts()
                }

                _pendingProducts.value = products
            } finally {
                _loading.value = false
            }
        }
    }

    fun reviewProduct(product: Product, isApproved: Boolean) {
        viewModelScope.launch {
            repository.reviewProduct(product, isApproved)
            // Actualizar la lista de productos pendientes
            fetchPendingProducts()
        }
    }
}
