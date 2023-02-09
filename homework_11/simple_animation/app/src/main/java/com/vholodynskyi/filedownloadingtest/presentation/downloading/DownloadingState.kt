package com.vholodynskyi.filedownloadingtest.presentation.downloading

data class DownloadingState(
    val isLoading: Boolean = false,
    val file: String = "",
    val error: String = "",
    val filePercent : Float = 0.0f
)