package com.example.christmasapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = CreateMusicalScoreFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, mainFragment)
            .commit()
    }
}
