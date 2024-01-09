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

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        userDao = AppDatabase.getInstance(requireActivity().applicationContext).getUserDao()
        usersDao = AppDatabase.getInstance(requireActivity().applicationContext).getUsersDao()

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        val login_button = rootView.findViewById<Button>(R.id.loginButton)
        val username_input = rootView.findViewById<EditText>(R.id.username)
        val password_input = rootView.findViewById<EditText>(R.id.password)
        login_button.setOnClickListener {
            var userFound = false
            val password = passwordEncoder.encodePassword(password_input.text.toString())
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val logIn = usersDao.logIn(username_input.text.toString(),password)
                    if (logIn != null){
                        val user = User(0,logIn.username)
                        userDao.insert(user)
                        userFound = true
                    }
                }
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
         * @return A new instance of fragment Login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {

            }
    }
}