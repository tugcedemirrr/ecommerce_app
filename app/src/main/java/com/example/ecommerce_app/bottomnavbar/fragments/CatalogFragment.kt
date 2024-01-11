package com.example.ecommerce_app.bottomnavbar.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ecommerce_app.model.UserViewModel

class CatalogFragment : Fragment() {
    private val sharedViewModel : UserViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatalogFragment().apply {

            }
    }
}