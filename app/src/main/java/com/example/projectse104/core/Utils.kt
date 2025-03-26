package com.example.projectse104.core

import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "AppTag"

fun logErrorMessage(
    errorMessage: String
) = Log.e(TAG, errorMessage)

fun showToastMessage(
    context: Context,
    message: String
) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()