package com.example.firstcoroutinesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Starting coroutines
        GlobalScope.launch {
            // Instructions that we want our coroutine to execute
            // This is a coroutine scope
            // GlobalScope will live as long as our application does if it does not get finished

            delay(5000)
            // delay will pause the current coroutine and won't block the whole thread

            Log.d(TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")
        }
        Log.d(TAG, "Hello from thread ${Thread.currentThread().name}")

        // If main thread get finished, all other thread, coroutines will get canceled!!
    }
}