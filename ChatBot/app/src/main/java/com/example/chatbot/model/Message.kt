package com.example.chatbot.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    val id: String = System.currentTimeMillis().toString(),
    val sender: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFromUser: Boolean
) {
    fun getFormattedTime(): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    companion object {
        const val SENDER_USER = "You"
        const val SENDER_BOT = "Llama"
    }
}
