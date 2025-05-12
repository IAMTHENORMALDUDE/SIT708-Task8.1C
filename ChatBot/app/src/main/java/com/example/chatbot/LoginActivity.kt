package com.example.chatbot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var tilUsername: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        tilUsername = findViewById(R.id.tilUsername)
        etUsername = findViewById(R.id.etUsername)
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            if (username.isEmpty()) {
                tilUsername.error = getString(R.string.error_empty_username)
            } else {
                tilUsername.error = null
                navigateToChatScreen(username)
            }
        }
    }

    private fun navigateToChatScreen(username: String) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra(ChatActivity.EXTRA_USERNAME, username)
        }
        startActivity(intent)
        finish() // Close login activity
    }
}
