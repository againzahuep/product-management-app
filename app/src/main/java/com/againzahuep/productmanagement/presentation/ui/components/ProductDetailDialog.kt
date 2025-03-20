package com.againzahuep.productmanagement.presentation.ui.components

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.againzahuep.productmanagement.R
import com.againzahuep.productmanagement.data.model.Product
import com.againzahuep.productmanagement.databinding.DialogProductDetailBinding

class ProductDetailDialog(
    context: Context,
    private val product: Product
) : Dialog(context) {

    private lateinit var binding: DialogProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            textViewTitle.text = product.title
            textViewCategory.text = product.category
            textViewPrice.text = "$${product.price}"
            textViewDescription.text = product.description

            val statusText = when (product.isApproved) {
                true -> context.getString(R.string.status_approved)
                false -> context.getString(R.string.status_rejected)
                null -> context.getString(R.string.status_pending)
            }
            textViewStatus.text = context.getString(R.string.status_label, statusText)

            val statusColor = when (product.isApproved) {
                true -> R.color.approved_green
                false -> R.color.rejected_red
                null -> R.color.pending_yellow
            }
            textViewStatus.setTextColor(context.getColor(statusColor))

            Glide.with(context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_empty)
                .into(imageViewProduct)

            buttonClose.setOnClickListener {
                dismiss()
            }
        }
    }
}
