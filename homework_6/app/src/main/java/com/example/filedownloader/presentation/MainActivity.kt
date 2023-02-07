package com.example.filedownloader.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.filedownloader.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val viewModel:DownloadFileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnDownload.setOnClickListener {
            lifecycleScope.launch {
                viewModel.state.collectLatest { state ->
                    if(state.isLoading){
                        binding!!.progressCircular.visibility = View.VISIBLE
                        binding!!.btnCancel.setOnClickListener {
                            viewModel.cancelDownloading()
                            Toast.makeText(this@MainActivity ,"Download cancel", Toast.LENGTH_SHORT).show()
                            binding!!.progressCircular.visibility = View.GONE
                        }

                    }

                    binding!!.txtText.text = state.file

                }
            }
        }



    }
}