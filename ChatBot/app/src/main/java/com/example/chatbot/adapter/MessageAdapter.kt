package com.example.chatbot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.R
import com.example.chatbot.model.Message

class MessageAdapter(private val messages: List<Message>) : 
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_BOT = 2
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isFromUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_USER) {
            R.layout.item_message
        } else {
            R.layout.item_message_bot
        }
        
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false) // Attach to parent: false
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSender: TextView = itemView.findViewById(R.id.tvSender)
        private val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        private val cardMessage: CardView = itemView.findViewById(R.id.cardMessage)

        fun bind(message: Message) {
            tvSender.text = message.sender
            tvMessage.text = message.content
            tvTimestamp.text = message.getFormattedTime()

            val context = itemView.context
            
            // Customize appearance based on sender
            if (message.isFromUser) {
                // User message styling
                cardMessage.setCardBackgroundColor(ContextCompat.getColor(context, R.color.message_background))
                tvMessage.setTextColor(ContextCompat.getColor(context, R.color.message_text))
            } else {
                // Bot message styling
                cardMessage.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bot_message_background))
                tvMessage.setTextColor(ContextCompat.getColor(context, R.color.bot_message_text))
            }
        }
    }
}
