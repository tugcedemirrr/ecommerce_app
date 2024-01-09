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

    }


}