package com.example.chatbot.api

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("status")
    val status: String?
)
