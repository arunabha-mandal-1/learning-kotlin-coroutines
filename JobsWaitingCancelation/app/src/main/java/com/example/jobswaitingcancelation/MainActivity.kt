package com.example.jobswaitingcancelation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Whenever a run a new coroutine, it actually returns a job which we can save in a variable.
        /*val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                Log.d(TAG, "Coroutine is still working...")
                delay(1000L)
            }
        }*/

        // Wait for the job to be finished. Call .join() from coroutines scope cuz it is suspend function.
//        runBlocking {
//            job.join()
//            Log.d(TAG, "Main thread is continuing...")
//        }

        // Canceling a job. 'job' won't go on if we cancel it. After 2 sec, it will continue with main thread.
//        runBlocking {
//            delay(2000L)
//            job.cancel()
//            Log.d(TAG, "Main thread is continuing...")
//        }

        /**
         * Cancellation of a coroutine is not always easy. Cancellation is actually cooperative which means our coroutines set
         * up to be correctly canceled. There needs to be enough time to tell the coroutine that it's been correctly canceled.
         * This scenario demonstrated below:
         */

//        val jobTwo = GlobalScope.launch(Dispatchers.Default) {
//            Log.d(TAG, "Starting long running calculation...")
//            for (i in 30..50) {
//
//                // Coroutine is so busy with calculation that it doesn't check cancellation request that's why we've to add a check.
//                if (isActive) {
//                    Log.d(TAG, "Result for $i: ${fib(i)}")
//                }
//            }
//            Log.d(TAG, "Ending long running calculation!")
//        }
//        runBlocking {
//            delay(2000L)
//            jobTwo.cancel()
//            Log.d(TAG, "Canceled job!!")
//        }

        /**
         * In practice the reason we want to cancel a job is due to some timeout.
         * Let's say, we have a network call and it's taking too much time so we want to cancel it.
         * For this purpose coroutines comes with a really powerful suspend function named 'withTimeout' wrapped around our long running cancellation.
         * Demonstrated below:
         */

        val jobThree = GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG, "Starting long running calculation...")

            // If the code inside withTimeout block needs more than 3 sec, it will cancel automatically.
            // Equivalent to starting a new coroutine and delaying it for 3 sec and then canceling it after that.
            withTimeout(3000L) {
                for (i in 30..50) {
                    if (isActive) {
                        Log.d(TAG, "Result for $i: ${fib(i)}")
                    }
                }
            }

            Log.d(TAG, "Ending long running calculation...")
        }

    }

    fun fib(n: Int): Long {
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}