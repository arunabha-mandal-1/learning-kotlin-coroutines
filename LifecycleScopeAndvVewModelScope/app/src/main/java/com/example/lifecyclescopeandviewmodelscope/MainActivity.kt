package com.example.lifecyclescopeandviewmodelscope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // most of the time it's a bad practice to use global scope everytime
        // two predefined scope: lifeCycleScope and viewModelScope

        val btnStartActivity = findViewById<Button>(R.id.btnStartActivity)

        // bad practice
        btnStartActivity.setOnClickListener {
//            GlobalScope.launch {
//                while (true) {
//                    delay(1000L)
//                    Log.d(TAG, "Still running...")
//                }
//            }
//            GlobalScope.launch {
//                delay(5000L)
//                Intent(this@MainActivity, SecondActivity::class.java).also {
//                    startActivity(it)
//                    finish()
//                }
//            }

            // our MainActivity is destroyed but our coroutine from MainActivity keeps running
            // cuz we've defined it in GlobalScope
            // so it won't be destroyed if our activity is destroyed
            // instead it will be destroyed when our whole application is destroyed
            // that can create memory leak
            // if the coroutine started in MainActivity and uses resources of that MainActivity which is now destroyed,
            // the resources won't be garbage collected if tho the activity is destroyed
            // cuz coroutines still uses those resources

            // to solve this problem we should use lifeCycleScope instead of GlobalScope

            // good practice

            // it will stick this coroutine to the lifecycle of our activity
            // activity destroyed => coroutines destroyed
            lifecycleScope.launch {
                while (true) {
                    delay(1000L)
                    Log.d(TAG, "Still running...")
                }
            }
            GlobalScope.launch {
                delay(5000L)
                Intent(this@MainActivity, SecondActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }

            // viewModelScope: keep ur coroutine alive as long as your viewModel is alive
        }
    }
}