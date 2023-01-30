package com.example.filedownloader

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filedownloader.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.btnDownload?.setOnClickListener {
            val fileUrl = binding?.etDownloadFile?.text.toString()
            if (fileUrl.isNotEmpty()) {
                val downloader = AndroidDownloader(this)
                downloader.downloadFile(fileUrl)
            }
        }
        binding?.btnCancel?.setOnClickListener {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            val downloader = AndroidDownloader(this)
            downloader.cancelFile(id)

        }

        printFlow()
    }


    private fun printFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            val flowOfStrings = flowOf("a", "b", "c", "d", "e","f","g").onEach { delay(15) }
            val combineFlowWithExtensionFunction = flowOfStrings.combineElements()
            combineFlowWithExtensionFunction.collect {
                println(it)
            }

        }
    }

    private fun Flow<String>.combineElements(): Flow<String> =
        this.combine(this.filter { it == "a" || it == "c" || it == "e" }) { firstElement, secondElement ->
            "$firstElement ---> $secondElement"
        }
}