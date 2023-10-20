package com.example.coroutinescontext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
        Dispatchers.Main: It will start coroutine in the main thread. It's useful for UI operations within coroutines. We can change UI only from main thread.
        Dispatchers.IO: It is useful for all kind of data related operations such as networking, writing to database, reading and writing to files.
        Dispatchers.Default: We may use it if we are planning for complex and long running calculations that will block the main thread.
        Dispatchers.Unconfined: It's not confined to a specific thread. If you run a coroutine in unconfined that calls a suspend function, it will stay in the thread that suspend function resumed.
        newSingleThreadContext(name_of_the_thread): It starts a new thread and run the coroutine in that thread.

        # You can easily switch coroutine context from within a coroutine.
        # We shouldn't make network request in the main thread but we can only change UI in the main thread. We can solve this problem using coroutine!

        **/

        // We do not want network call in the main thread.
        GlobalScope.launch(Dispatchers.IO){
            Log.d(TAG, "Starting coroutine in thread in ${Thread.currentThread().name}")
            val data = deNetworkCall()

            // This line will cause app crash as we are trying to change UI from IO dispatcher
//            findViewById<TextView>(R.id.tvHello).text = data

            // Changing context, code inside this block will be executed in the main thread
            withContext(Dispatchers.Main){
                Log.d(TAG, "Setting text in thread ${Thread.currentThread().name}")
                findViewById<TextView>(R.id.tvHello).text = data
            }
        }
    }

    suspend fun deNetworkCall(): String {
        delay(5000)
        return "The is Data"
    }
}