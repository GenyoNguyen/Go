package com.example.projectse104.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object UriUtils {

    fun copyUriToTempFile(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.let { stream ->
                // Tạo file tạm thời
                val tempFile = File(context.cacheDir, "temp_profile_pic_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(tempFile)

                // Copy dữ liệu
                stream.copyTo(outputStream)

                // Đóng streams
                stream.close()
                outputStream.close()

                tempFile.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getFileFromUri(context: Context, uri: Uri): String? {
        // Thử lấy đường dẫn thực tế trước
        val realPath = getRealPathFromUri(context, uri)
        if (realPath != null && File(realPath).exists()) {
            return realPath
        }

        // Nếu không được, copy sang file tạm thời
        return copyUriToTempFile(context, uri)
    }
}