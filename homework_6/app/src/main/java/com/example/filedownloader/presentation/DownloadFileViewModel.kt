package com.example.filedownloader.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filedownloader.common.Resource
import com.example.filedownloader.domain.DownloadFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DownloadFileViewModel @Inject constructor(
    private val downloadFileUseCase: DownloadFileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DownloadingState())
    val state: StateFlow<DownloadingState> = _state

    private val fileUrl = "https://github.com/SaBeat/E-Commerce"

    private val testJob: Job

    init {
        testJob = downloadFiles()
    }

    private fun downloadFiles(): Job {
        return downloadFileUseCase(fileUrl)
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Error -> DownloadingState(error = result.message ?: "")
                    is Resource.Loading -> DownloadingState(isLoading = true)
                    is Resource.Success -> DownloadingState(file = result.data ?: "")
                }
            }.onCompletion {
                if (it is CancellationException) {
                    _state.value = DownloadingState(error = "Downloading is cancelled")
                }
            }.launchIn(viewModelScope)
    }

    fun cancelDownloading() {
        if (testJob.isActive) {
            testJob.cancel()
        }
    }

}