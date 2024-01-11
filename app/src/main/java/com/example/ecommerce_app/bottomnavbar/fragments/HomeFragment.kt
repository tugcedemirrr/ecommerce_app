package com.example.ecommerce_app.bottomnavbar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.ecommerce_app.R
import com.example.ecommerce_app.bottomnavbar.MainActivity
import com.example.ecommerce_app.database.AppDatabase
import com.example.ecommerce_app.database.Items
import com.example.ecommerce_app.database.User
import com.example.ecommerce_app.database.UserDao
import com.example.ecommerce_app.databinding.FragmentCatalogBinding
import com.example.ecommerce_app.model.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import services.ProductsAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


// Deze functie haalt de gebruikersgegevens op van de database op de IO-thread met behulp van coroutines.
// Het gebruik van 'suspend' geeft aan dat deze functie opgeschort kan worden en moet worden aangeroepen vanuit een coroutine scope.
suspend fun getLogin(userDao: UserDao) : User {
    return withContext(Dispatchers.IO){
        userDao.getUser()
    }
}
class HomeFragment : Fragment() {
    private val sharedViewModel : UserViewModel by activityViewModels()
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialiseren van de UserDao met de applicationDatabase
        userDao = AppDatabase.getInstance(requireActivity().applicationContext).getUserDao()
        // Controleer of de gebruiker is ingelogd met behulp van de gedeelde ViewModel
        if (!sharedViewModel.isLoggedIn()) {
            // Als de gebruiker niet is ingelogd, voer een coroutine uit om de gebruikersnaam op te halen vanuit de database
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUser()
                }
                // Als de gebruiker wordt gevonden, stel de gebruikersnaam in en navigeer naar het CatalogFragment
                if (user != null) {
                    sharedViewModel.setUsername(user.username!!)
                    createFragment(CatalogFragment())
                } else {
                    sharedViewModel.setUsername("")
                }
            }

        }
        else {
            // Als de gebruiker is ingelogd, navigeer direct naar het CatalogFragment
            createFragment(CatalogFragment())
        }

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val login = rootView.findViewById<View>(R.id.goToLoginPage) as Button
        val register = rootView.findViewById<View>(R.id.goToRegisterPage) as Button
        // OnClickListeners toevoegen aan de knoppen om de juiste fragmenten te maken en weer te geven
        login.setOnClickListener {
            createFragment(LoginFragment())
        }
        register.setOnClickListener {
            createFragment(RegisterFragment())
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
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}