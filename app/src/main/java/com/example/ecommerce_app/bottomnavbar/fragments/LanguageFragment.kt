package com.example.ecommerce_app.bottomnavbar.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import com.example.ecommerce_app.R
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [LanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LanguageFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_language, container, false)
        val set_du = rootView.findViewById<View>(R.id.set_dutch) as TextView
        val set_en = rootView.findViewById<View>(R.id.set_english) as TextView
        set_du.setOnClickListener {
            val locale = Locale("nl" , "NL")
            val configuration = Configuration()
            configuration.setLocale(locale)
            resources.configuration.locale = locale
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, LanguageFragment.newInstance())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
        Toast.makeText(activity,"english",Toast.LENGTH_SHORT).show();
        set_en.setOnClickListener {
        }
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LanguageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LanguageFragment().apply {

            }
    }
}