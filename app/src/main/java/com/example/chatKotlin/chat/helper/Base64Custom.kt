package com.example.chatKotlin.chat.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class Base64Custom {

    @RequiresApi(Build.VERSION_CODES.O)
    fun encodeBase64(args: String): String {
        return Base64.getEncoder().encodeToString(args.toByteArray()).replace("(\\n|\\r)", "")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decodeBase64(args: String): String {
        val decodedBytes = Base64.getDecoder().decode(args)
        return String(decodedBytes)
    }
}