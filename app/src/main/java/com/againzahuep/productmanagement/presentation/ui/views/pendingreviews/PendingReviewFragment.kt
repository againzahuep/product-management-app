package com.againzahuep.productmanagement.presentation.ui.views.pendingreviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.againzahuep.productmanagement.R
import com.againzahuep.productmanagement.data.model.Product
import com.againzahuep.productmanagement.databinding.FragmentPendingReviewBinding
import com.againzahuep.productmanagement.presentation.ui.components.ProductDetailDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingReviewFragment : Fragment() {

    private var _binding: FragmentPendingReviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PendingReviewViewModel by viewModels()
    private lateinit var adapter: PendingProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendingReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = PendingProductAdapter(
            onProductClicked = { product ->
                showProductDetails(product)
            },
            onProductReviewed = { product, isApproved ->
                viewModel.reviewProduct(product, isApproved)
                val message = if (isApproved) {
                    getString(R.string.product_approved)
                } else {
                    getString(R.string.product_rejected)
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewPendingProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@PendingReviewFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.pendingProducts.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showProductDetails(product: Product) {
        ProductDetailDialog(requireContext(), product).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}