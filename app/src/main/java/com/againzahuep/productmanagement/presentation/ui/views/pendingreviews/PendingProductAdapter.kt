package com.againzahuep.productmanagement.presentation.ui.views.pendingreviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.againzahuep.productmanagement.R
import com.againzahuep.productmanagement.data.model.Product

class PendingProductAdapter(
    private val onProductClicked: (Product) -> Unit,
    private val onProductReviewed: (Product, Boolean) -> Unit
) : ListAdapter<Product, PendingProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pending_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitlePending)
        private val priceTextView: TextView = itemView.findViewById(R.id.textViewPricePending)
        private val productImageView: ImageView = itemView.findViewById(R.id.imageViewProductPending)
        private val approveCheckBox: CheckBox = itemView.findViewById(R.id.checkBoxApprove)
        private val rejectCheckBox: CheckBox = itemView.findViewById(R.id.checkBoxReject)

        fun bind(product: Product) {
            titleTextView.text = product.title
            priceTextView.text = "$${product.price}"

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_empty)
                .into(productImageView)

            // Reiniciar estado de checkboxes
            approveCheckBox.isChecked = false
            rejectCheckBox.isChecked = false

            approveCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    rejectCheckBox.isChecked = false
                    onProductReviewed(product, true)
                }
            }

            rejectCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    approveCheckBox.isChecked = false
                    onProductReviewed(product, false)
                }
            }

            itemView.setOnClickListener {
                onProductClicked(product)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}