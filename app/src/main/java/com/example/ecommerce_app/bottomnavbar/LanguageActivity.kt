package com.example.ecommerce_app.bottomnavbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ecommerce_app.R
import com.example.ecommerce_app.bottomnavbar.fragments.HomeFragment
import com.example.ecommerce_app.bottomnavbar.fragments.LanguageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        /*val bottomNav = findViewById<BottomNavigationView>(R.id.goToLanguageFragment)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        // as soon as the application opens the first fragment should
        // be shown to the user in this case it is algorithm fragment
        supportFragmentManager.beginTransaction().replace(R.id.fl_wrapper, HomeFragment()).commit()
*/
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper,fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()

        val textView = findViewById<TextView>(R.id.goToLanguageFragment)
        textView.setOnClickListener{
            val fragment = LanguageFragment()
            loadFragment(fragment)
        }


    }
}