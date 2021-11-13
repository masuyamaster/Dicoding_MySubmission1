package com.roziqrizal.mysubmission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        try {
            Thread.sleep(2000)
            startActivity(Intent(this@Splashscreen, MainActivity::class.java))

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}