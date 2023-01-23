package com.example.filedownloader

interface Downloader {
    fun downloadFile(url: String): Long
    fun cancelFile(id:Long)
}