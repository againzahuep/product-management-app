package com.againzahuep.productmanagement.presentation.ui.views.reviewed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.againzahuep.productmanagement.R
import com.againzahuep.productmanagement.databinding.FragmentReviewedBinding
import com.againzahuep.productmanagement.presentation.ui.components.ProductDetailDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewedFragment : Fragment() {

    private var _binding: FragmentReviewedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewedViewModel by viewModels()
    private lateinit var adapter: ReviewedProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ReviewedProductAdapter(
            onProductClicked = { product ->
                ProductDetailDialog(requireContext(), product).show()
            },
            onDeleteClicked = { product ->
                viewModel.deleteProduct(product)
                Toast.makeText(
                    requireContext(),
                    R.string.product_deleted,
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.recyclerViewReviewedProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ReviewedFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reviewedProducts.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        // Cambiar la visibilidad del mensaje "sin productos" cuando la lista está vacía
        adapter.registerAdapterDataObserver(object : androidx.recyclerview.widget.RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.textViewEmpty.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}