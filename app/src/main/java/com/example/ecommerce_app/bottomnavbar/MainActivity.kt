package com.example.ecommerce_app.bottomnavbar

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ecommerce_app.R
import com.example.ecommerce_app.bottomnavbar.fragments.FavouritesFragment
import com.example.ecommerce_app.bottomnavbar.fragments.HomeFragment
import com.example.ecommerce_app.bottomnavbar.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.util.Locale


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"hallo")
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        // as soon as the application opens the first fragment should
        // be shown to the user in this case it is algorithm fragment
        supportFragmentManager.beginTransaction().replace(R.id.fl_wrapper, HomeFragment()).commit()


        val changeLanguage = findViewById<Button>(R.id.changeMyLang)
        changeLanguage.setOnClickListener{
            val locale = Locale("nl")
            val configuration = Configuration()
            configuration.setLocale(locale)


            Toast.makeText(this,"Button Clicked2", Toast.LENGTH_SHORT).show();
        }


    }
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        // By using switch we can easily get the
        // selected fragment by using there id
        lateinit var selectedFragment: Fragment
        when (it.itemId) {
            R.id.ic_home -> {
                selectedFragment = HomeFragment()
            }
            R.id.ic_favorite -> {
                selectedFragment = FavouritesFragment()
            }
            R.id.ic_settings -> {
                selectedFragment = SettingsFragment()
            }
        }
        // It will help to replace the
        // one fragment to other.
        supportFragmentManager.beginTransaction().replace(R.id.fl_wrapper, selectedFragment).commit()
        true
    }

}