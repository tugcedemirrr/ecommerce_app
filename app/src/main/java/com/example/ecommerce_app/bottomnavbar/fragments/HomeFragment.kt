package com.example.ecommerce_app.bottomnavbar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.ecommerce_app.R
import com.example.ecommerce_app.bottomnavbar.MainActivity
import com.example.ecommerce_app.database.AppDatabase
import com.example.ecommerce_app.database.User
import com.example.ecommerce_app.database.UserDao
import com.example.ecommerce_app.model.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

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
        userDao = AppDatabase.getInstance(requireActivity().applicationContext).getUserDao()
        if (!sharedViewModel.isLoggedIn()) {
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUser()

                }
                if (user != null) {
                    sharedViewModel.setUsername(user.username!!)
                    createFragment(CatalogFragment())
                } else {
                    sharedViewModel.setUsername("")
                }
            }

        }
        else {
            createFragment(CatalogFragment())
        }

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val login = rootView.findViewById<View>(R.id.goToLoginPage) as Button
        val register = rootView.findViewById<View>(R.id.goToRegisterPage) as Button
        login.setOnClickListener {
            createFragment(LoginFragment())
        }
        register.setOnClickListener {
            createFragment(RegisterFragment())
        }
        return rootView
    }

    private fun createFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
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