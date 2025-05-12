package com.example.chatbot.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatService {
    @GET("chat")
    fun getChatResponse(@Query("message") message: String): Call<ChatResponse>
    
    @GET("test")
    fun testApi(): Call<TestResponse>
}
