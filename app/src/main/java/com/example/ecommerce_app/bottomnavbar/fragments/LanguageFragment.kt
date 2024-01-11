package com.example.ecommerce_app.bottomnavbar.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import com.example.ecommerce_app.R
import com.example.ecommerce_app.bottomnavbar.MainActivity
import java.util.Locale

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
        // Vinden van TextView-elementen in de lay-out.
        val set_du = rootView.findViewById<View>(R.id.set_dutch) as TextView
        val set_en = rootView.findViewById<View>(R.id.set_english) as TextView

        // Toevoegen van OnClickListener aan het Nederlandse TextView-element
        set_du.setOnClickListener {
            // Locale creeren voor de Nederlandse taal en configureer de app-resources die overeen komen
           /* val locale = Locale("nl" , "NL")
            val configuration = Configuration()
            configuration.setLocale(locale)
            resources.configuration.locale = locale
            // Start een transactie om het huidige fragment te vervangen door een nieuwe instantie van het LanguageFragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, LanguageFragment.newInstance())
            transaction.disallowAddToBackStack()
            transaction.commit()

            val locale = Locale("nl" , "NL")
            val configuration = Configuration()
            configuration.setLocale(locale)
            resources.configuration.locale = locale
            recreate() */
            (requireActivity() as MainActivity).changeLanguage(Locale("nl","NL"))
            createFragment(HomeFragment())
        }
        // Toevoegen van OnClickListener aan het Engelse TextView-element.
        set_en.setOnClickListener {
            (requireActivity() as MainActivity).changeLanguage(Locale("en","rUS"))
            createFragment(HomeFragment())
        }
        return rootView
    }

    // Hulpmethode om een nieuw fragment te maken en weer te geven
    private fun createFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LanguageFragment().apply {

            }
    }
}