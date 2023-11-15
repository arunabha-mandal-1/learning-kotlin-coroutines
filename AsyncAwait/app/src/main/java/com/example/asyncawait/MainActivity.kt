package com.example.asyncawait

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * If we have several suspend functions and execute then on coroutine, they are sequential by default.
         * But if we want to do two network, we don't want to be one after another. We want them to be executed at the same time.
         */
    }

    suspend fun networkCallOne(): String{
        delay(5000L)
        return "This is data 1"
    }

    suspend fun networkCallTwo(): String{
        delay(5000L)
        return "This is data 2"
    }
}