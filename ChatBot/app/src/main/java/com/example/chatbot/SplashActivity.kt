package com.example.chatbot

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chatbot.api.ApiClient
import com.example.chatbot.api.TestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    
    private val splashTimeOut: Long = 2000 // 2 seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Test API connection while showing splash screen
        testApiConnection()
        
        // Navigate to login screen after delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToLoginScreen()
        }, splashTimeOut)
    }
    
    private fun testApiConnection() {
        ApiClient.chatService.testApi().enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                // API test successful, but we'll still wait for the splash timeout
                // before navigating to login screen
            }
            
            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                // API test failed, but we'll still navigate to login screen
                // The actual API errors will be handled in the chat screen
            }
        })
    }
    
    private fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
