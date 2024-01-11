package services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.database.Items
import com.example.ecommerce_app.databinding.FragmentProductItemBinding

class ProductsAdapter(
    private val itemsList: List<Items>
): RecyclerView.Adapter<ProductViewHolder>() {

    private lateinit var binding: FragmentProductItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = FragmentProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemsList.size

}