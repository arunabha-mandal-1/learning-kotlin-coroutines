package com.example.suspendfunctions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        /*
        Suspend functions can be called from another suspend function or from a coroutine.
        E.g. delay is a suspend function.
        **/

        // We cannot call it from here cuz it's a suspend function
//        val ans = doNetworkCall()


        GlobalScope.launch {

            // 1
//            val data1 = doNetworkCall1()
//            Log.d(TAG, data1) // after 5 sec
//            val data2 = doNetworkCall2()
//            Log.d(TAG, data2) // after 10 sec

            //2
            val data1 = doNetworkCall1()
            val data2 = doNetworkCall2()
            Log.d(TAG, data1) // after 10 sec
            Log.d(TAG, data2) // after 10 sec

            // Delays add up sequentially as we proceed cuz they are running in the same coroutine
        }
    }

    suspend fun doNetworkCall1(): String {
        delay(5000)
        return "This is your Data1!!"
    }

    suspend fun doNetworkCall2(): String {
        delay(5000)
        return "This is your Data2!!"
    }
}