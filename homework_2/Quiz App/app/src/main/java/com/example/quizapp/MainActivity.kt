package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    private val listOfCoroutines = mutableListOf<String>()
    private val stateOfCoroutines = MutableStateFlow("-1")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnSync?.setOnClickListener {
            runSync()
        }

        binding?.btnAsync?.setOnClickListener {
            runAsync()
        }

        lifecycleScope.launch {
            stateOfCoroutines.collect {indexOfCoroutine ->
                if (indexOfCoroutine != "-1") {
                    Log.d("Tag", indexOfCoroutine)
                }
            }
        }

    }

    private fun runSync() {
        println("runSync method.")
        CoroutineScope(IO).launch {

            repeat(1000) {index ->
                launch {
                    listOfCoroutines.add(index.toString())
                }
                delay(300)
                doWork(listOfCoroutines[index])
            }

        }


    }

    private fun runAsync() {
        println("runAsync method.")

        lifecycleScope.launch {

            async(IO) {

                repeat(1000) {
                    async(IO) {
                        delay(300)
                        listOfCoroutines.add(it.toString())
                    }
                }

                repeat(1000) {
                    delay(500)
                    doWork(listOfCoroutines[it])

                }
            }

        }
    }

    private suspend fun doWork(name: String) {
        delay(500)
        stateOfCoroutines.update { "$name completed." }
    }
}