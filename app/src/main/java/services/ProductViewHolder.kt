package services

import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.database.Items
import com.example.ecommerce_app.databinding.FragmentProductItemBinding

class ProductViewHolder(
    private val binding: FragmentProductItemBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(items: Items){
        binding.items = items
    }
}