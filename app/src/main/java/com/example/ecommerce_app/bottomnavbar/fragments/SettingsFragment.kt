package com.example.ecommerce_app.bottomnavbar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce_app.R
import com.example.ecommerce_app.database.AppDatabase
import com.example.ecommerce_app.database.User
import com.example.ecommerce_app.database.UserDao
import com.example.ecommerce_app.model.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : Fragment() {
    private val sharedViewModel : UserViewModel by activityViewModels()
    private lateinit var dao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialiseren van UserDao met de applicationDatabase
        dao = AppDatabase.getInstance(requireActivity().applicationContext).getUserDao()

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        // Elementen vinden in layout
        val change_language = rootView.findViewById<View>(R.id.goToLanguageFragment) as TextView
        val change_theme = rootView.findViewById<View>(R.id.goToThemeFragment) as TextView
        val log_out = rootView.findViewById<View>(R.id.goToHomeFragment) as TextView
        // OnclickListeners toevoegen aan elementen
        change_language.setOnClickListener {
            createFragment(LanguageFragment())
        }
        change_theme.setOnClickListener {
            createFragment(ThemeFragment())
        }

        log_out.setOnClickListener {
            // Gebruiken van coroutines om IO-bewerkingen uit te voeren
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Haal de huidige gebruiker op en verwijder deze uit de lokale database
                    val user = dao.getUser()
                    if (user != null) {
                        dao.delete(user)
                    }
                }
                // Stel de gebruikersnaam in op een lege string en navigeer naar het HomeFragment
                sharedViewModel.setUsername("")
                createFragment(HomeFragment())
            }
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
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}