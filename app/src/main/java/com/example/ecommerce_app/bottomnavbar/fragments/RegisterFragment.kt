package com.example.ecommerce_app.bottomnavbar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce_app.R
import com.example.ecommerce_app.database.AppDatabase
import com.example.ecommerce_app.database.User
import com.example.ecommerce_app.database.UserDao
import com.example.ecommerce_app.database.Users
import com.example.ecommerce_app.database.UsersDao
import com.example.ecommerce_app.model.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import services.PasswordEncoder

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private val sharedViewModel : UserViewModel by activityViewModels()
    private lateinit var usersDao: UsersDao
    private val passwordEncoder = PasswordEncoder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersDao = AppDatabase.getInstance(requireActivity().applicationContext).getUsersDao()

        val rootView = inflater.inflate(R.layout.fragment_register, container, false)
        val register_button = rootView.findViewById<Button>(R.id.registerButton)
        val username_input = rootView.findViewById<EditText>(R.id.username)
        val password_input = rootView.findViewById<EditText>(R.id.password)
        val password2_input = rootView.findViewById<EditText>(R.id.password2)
        register_button.setOnClickListener {
            var userAdded = false
            var userExists = false
            val password = passwordEncoder.encodePassword(password_input.text.toString())
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (password_input.text.toString() == password2_input.text.toString()) {
                        userExists = usersDao.userExists(username_input.text.toString())
                        if (!userExists) {
                            val users = Users(0, username_input.text.toString(), password)
                            usersDao.insert(users)
                            userAdded = true
                        }
                    }
                }
                if (userAdded) {
                    sharedViewModel.setUsername(username_input.text.toString())
                    sharedViewModel.setPassword(password)
                    createFragment(CatalogFragment())
                }
                else if (userExists) {
                    Toast.makeText(activity, "User already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(activity, "Password mismatch", Toast.LENGTH_SHORT).show();
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {

            }
    }
}