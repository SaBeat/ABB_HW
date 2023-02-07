package com.example.filedownloader.presentation

data class DownloadingState(
    val isLoading: Boolean = false,
    val file: String = "",
    val error: String = ""
)