package com.example.wannab.singleton

import android.content.Context
import android.net.Uri
import java.io.FileDescriptor

class FileManager private constructor(context: Context) {
    private val contentResolver by lazy {
        context.contentResolver
    }

    companion object {
        private var instance: FileManager? = null
        fun getInstance(context: Context): FileManager {
            return instance ?: synchronized(this) {
                FileManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    fun uriToFile(uri: Uri, mode: String): FileDescriptor? {
        return contentResolver.openFileDescriptor(uri, mode)?.fileDescriptor
    }
}