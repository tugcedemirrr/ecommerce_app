package com.example.ecommerce_app.bottomnavbar

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.ecommerce_app.R
import com.example.ecommerce_app.bottomnavbar.fragments.FavouritesFragment
import com.example.ecommerce_app.bottomnavbar.fragments.HomeFragment
import com.example.ecommerce_app.bottomnavbar.fragments.LanguageFragment
import com.example.ecommerce_app.bottomnavbar.fragments.SettingsFragment
import com.example.ecommerce_app.database.AppDatabase
import com.example.ecommerce_app.database.Items
import com.example.ecommerce_app.database.User
import com.example.ecommerce_app.databinding.FragmentCatalogBinding
import com.example.ecommerce_app.model.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import services.ProductsAdapter
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // BottomNavigationView vinden in de layout
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.fl_wrapper, HomeFragment()).commit()





    }

    fun changeLanguage(locale: Locale){
       // val changeLanguage = findViewById<Button>(R.id.changeMyLang)
        //changeLanguage.setOnClickListener{
            //val locale = Locale("nl" , "NL")
            val configuration = Configuration()
            configuration.setLocale(locale)
            resources.configuration.locale = locale
            val current = resources.configuration.locale
            val context = this@MainActivity
            val primaryLocale: Locale = context.resources.configuration.locales[0]
            Log.d("MainActivity", "primaryLocale: $primaryLocale")
            val allLocales = context.resources.configuration.locales
            Log.d("MainActivity", "allLocales: $allLocales")

            recreate();
        //}
    }

    // Listener voor het selecteren van items in BottomNavigationView
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        // Door switch te gebruiken, kunnen we eenvoudig het geselecteerde fragment krijgen op basis van de id
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

        // Vervangen van huidige fragment door het geselecteerde fragment
        supportFragmentManager.beginTransaction().replace(R.id.fl_wrapper, selectedFragment).commit()
        true
    }

    // Hulpmethode om een fragment te laden en weer te geven
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()


        val textView = findViewById<TextView>(R.id.goToLanguageFragment)

        textView.setOnClickListener {
            Toast.makeText(this,"hello", Toast.LENGTH_SHORT).show();
            /*val languageFragment = LanguageFragment()
            replaceFragment(languageFragment) */
        }
    }
}