package com.example.runblockingcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Before runBlocking.")

        // delay doesn't block the thread but runBlocking does

        // It won't block the main thread.
//        GlobalScope.launch(Dispatchers.Main) {  }

        // Starts a coroutine in the main thread and also block it i.e. this will block UI updates.
        // Equivalent to call delay function in the main thread.

        // 1. If I have a suspend function and I want to call it from main thread, we can use runBlocking if I don't care about asynchronous.
        // 2. Testing with JUnit, access suspend function within a test function.
        // 3. To quickly figure out the coroutines to figure out how they actually work behind the scenes.
        runBlocking {
            // runBlocking blocks main thread


            // new coroutines, they won't add up, won't be blocked
            launch(Dispatchers.IO) {
               delay(5000)
                Log.d(TAG, "Finished IO coroutine 1!")
            }

            launch(Dispatchers.IO) {
                delay(5000)
                Log.d(TAG, "Finished IO coroutine 2!")
            }

            Log.d(TAG, "Start of runBlocking.")
            delay(10000) // Similar to Thread.sleep(10000) outside of runBlocking
            Log.d(TAG, "End of runBlocking.")
        }
        Log.d(TAG, "After runBlocking")
    }
}