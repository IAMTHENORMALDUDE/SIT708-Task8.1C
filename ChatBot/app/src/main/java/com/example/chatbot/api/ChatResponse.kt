package com.example.chatbot.api

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("response")
    val response: String?,
    
    @SerializedName("error")
    val error: String?
)
