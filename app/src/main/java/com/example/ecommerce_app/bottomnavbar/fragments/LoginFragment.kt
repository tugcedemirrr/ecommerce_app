package com.example.ecommerce_app.bottomnavbar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce_app.R
import com.example.ecommerce_app.database.AppDatabase
import com.example.ecommerce_app.database.User
import com.example.ecommerce_app.database.UserDao
import com.example.ecommerce_app.database.UsersDao
import com.example.ecommerce_app.model.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import services.PasswordEncoder

class LoginFragment : Fragment() {
    private val sharedViewModel : UserViewModel by activityViewModels()
    private lateinit var userDao: UserDao
    private lateinit var usersDao: UsersDao
    private val passwordEncoder = PasswordEncoder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    } */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialiseren van UserDao en UsersDao met de applicationDatabase
        userDao = AppDatabase.getInstance(requireActivity().applicationContext).getUserDao()
        usersDao = AppDatabase.getInstance(requireActivity().applicationContext).getUsersDao()

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        // Vinden van de elementen in de lay-out.
        val login_button = rootView.findViewById<Button>(R.id.loginButton)
        val username_input = rootView.findViewById<EditText>(R.id.username)
        val password_input = rootView.findViewById<EditText>(R.id.password)

        // Toevoegen van een OnClickListener aan de login knop
        login_button.setOnClickListener {
            // Variable om bij te houden of de gebruiker is gevonden en gecodeerde password ophalen
            var userFound = false
            val password = passwordEncoder.encodePassword(password_input.text.toString())
            // Gebruiken van coroutines om IO uit te voeren
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Controleer of er een gebruiker is met de opgegeven inloggegevens
                    val logIn = usersDao.logIn(username_input.text.toString(),password)
                    if (logIn != null){
                        // Als de gebruiker wordt gevonden, voeg een nieuwe gebruiker toe aan de lokale database
                        val user = User(0,logIn.username)
                        userDao.insert(user)
                        userFound = true
                    }
                }
                // Als de gebruiker wordt gevonden, stel de gebruikersnaam in en navigeer naar het CatalogFragment
                if (userFound) {
                    sharedViewModel.setUsername(username_input.text.toString())
                    createFragment(CatalogFragment())
                }
                else{
                    Toast.makeText(activity, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }
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
            LoginFragment().apply {

            }
    }
}