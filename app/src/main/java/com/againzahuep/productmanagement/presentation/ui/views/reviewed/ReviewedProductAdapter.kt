package com.againzahuep.productmanagement.presentation.ui.views.reviewed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.againzahuep.productmanagement.R
import com.againzahuep.productmanagement.data.model.Product

class ReviewedProductAdapter(
    private val onProductClicked: (Product) -> Unit,
    private val onDeleteClicked: (Product) -> Unit
) : PagingDataAdapter<Product, ReviewedProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reviewed_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val priceTextView: TextView = itemView.findViewById(R.id.textViewPrice)
        private val productImageView: ImageView = itemView.findViewById(R.id.imageViewProduct)
        private val statusTextView: TextView = itemView.findViewById(R.id.textViewStatus)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(product: Product) {
            titleTextView.text = product.title
            priceTextView.text = "$${product.price}"

            // Mostrar estado de aprobación
            val statusText = when(product.isApproved) {
                true -> "Aprobado"
                false -> "Rechazado"
                null -> "Sin revisar"
            }
            statusTextView.text = statusText

            val statusColor = when(product.isApproved) {
                true -> R.color.approved_green
                false -> R.color.rejected_red
                null -> R.color.pending_yellow
            }
            statusTextView.setTextColor(itemView.context.getColor(statusColor))

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_empty)
                .into(productImageView)

            deleteButton.setOnClickListener {
                onDeleteClicked(product)
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
