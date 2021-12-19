package com.roziqrizal.mysubmission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        startActivity(Intent(this@Splashscreen, MainActivity::class.java))
        finish()

    }
}