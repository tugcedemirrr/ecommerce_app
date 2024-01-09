package com.example.ecommerce_app.bottomnavbar.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.example.ecommerce_app.R
import com.example.ecommerce_app.model.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 * Use the [CatalogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CatalogFragment : Fragment() {
    private val sharedViewModel : UserViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_catalog, container, false)
        val login_button = rootView.findViewById<View>(R.id.create_thread) as Button
        val username = sharedViewModel.username
        login_button.setOnClickListener {
            Log.d("catalog","hallo")
            runBlocking {
                launch {

                    Log.d("thread", username.value.toString())
                }
                launch {
                    Log.d("thread","world2")

                }
                Log.d("thread","hallo")
            }
            //createFragment(CatalogFragment())
        }
        return rootView
    }

    /*
    private fun createFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    } */


    /*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    } */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CatalogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatalogFragment().apply {

            }
    }
}