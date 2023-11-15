package com.example.asyncawait

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * If we have several suspend functions and execute then on coroutine, they are sequential by default.
         * But if we want to do two network, we don't want to be one after another. We want them to be executed at the same time.
         */

        // approach 1: sequential
//        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
//                val data1 = networkCallOne();
//                val data2 = networkCallOne();
//
//                Log.d(TAG, data1) // 10s
//                Log.d(TAG, data2) // 10s
//            }
//            Log.d(TAG, "Request took $time ms")
//        }

        // approach 2: two call in two coroutine, bad practice
//        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
//                var data1: String? = null
//                var data2: String? = null
//
//                val job1 = launch { data1 = networkCallOne() }
//                val job2 = launch { data2 = networkCallTwo() }
//
//                job1.join()
//                job2.join()
//
//                Log.d(TAG, "Data1 : $data1") // 5s
//                Log.d(TAG, "Data2 : $data2") // 5s
//            }
//            Log.d(TAG, "Request took $time ms")
//        }

        // approach 3: using async, it will start a new coroutine but it won't return a job rather it will return a deferred
        // this deferred can be used to get the result of the calculation or of the network call
        // when we want to get results asynchronously, we can use async instead of launch
        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val data1 = async { networkCallOne() }
                val data2 = async { networkCallTwo() }

                Log.d(TAG, "Data1 : ${data1.await()}") // 5s
                Log.d(TAG, "Data2 : ${data2.await()}") // 5s
            }
            Log.d(TAG, "Request took $time ms")
        }
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