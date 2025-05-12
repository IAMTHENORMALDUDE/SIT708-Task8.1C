package com.example.chatbot

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.adapter.MessageAdapter
import com.example.chatbot.api.ApiClient
import com.example.chatbot.api.ChatResponse
import com.example.chatbot.model.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {
    private lateinit var username: String
    private lateinit var rvMessages: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var messageAdapter: MessageAdapter
    private val messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        username = intent.getStringExtra(EXTRA_USERNAME) ?: "User"
        
        initViews()
        setupToolbar()
        setupRecyclerView()
        setupListeners()
        addWelcomeMessage()
    }

    private fun initViews() {
        rvMessages = findViewById(R.id.rvMessages)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.chat_with_llama)
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messageList)
        rvMessages.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).apply {
                stackFromEnd = true
            }
            adapter = messageAdapter
        }
    }

    private fun setupListeners() {
        btnSend.setOnClickListener {
            val message = etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                etMessage.text.clear()
            }
        }
    }

    private fun addWelcomeMessage() {
        val welcomeMessage = Message(
            sender = Message.SENDER_BOT,
            content = getString(R.string.welcome_message),
            isFromUser = false
        )
        addMessage(welcomeMessage)
    }

    private fun sendMessage(content: String) {
        // Add user message to the chat
        val userMessage = Message(
            sender = Message.SENDER_USER,
            content = content,
            isFromUser = true
        )
        addMessage(userMessage)
        
        // Show loading indicator
        progressBar.visibility = View.VISIBLE
        
        // Make API call to get bot response
        ApiClient.chatService.getChatResponse(content).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                progressBar.visibility = View.GONE
                
                if (response.isSuccessful) {
                    val chatResponse = response.body()
                    chatResponse?.response?.let { botResponse ->
                        val botMessage = Message(
                            sender = Message.SENDER_BOT,
                            content = botResponse,
                            isFromUser = false
                        )
                        addMessage(botMessage)
                    } ?: run {
                        showErrorMessage(chatResponse?.error ?: getString(R.string.error_network))
                    }
                } else {
                    showErrorMessage(getString(R.string.error_network))
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showErrorMessage(t.message ?: getString(R.string.error_network))
            }
        })
    }

    private fun addMessage(message: Message) {
        messageList.add(message)
        messageAdapter.notifyItemInserted(messageList.size - 1)
        rvMessages.scrollToPosition(messageList.size - 1)
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}
