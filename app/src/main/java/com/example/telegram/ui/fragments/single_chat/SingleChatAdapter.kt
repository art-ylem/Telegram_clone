package com.example.telegram.ui.fragments.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.R
import com.example.telegram.models.CommonModel
import com.example.telegram.database.CURRENT_UID
import com.example.telegram.utils.DiffUtilCallback
import com.example.telegram.utils.asTime
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessageCache = mutableListOf<CommonModel>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {

        val blockUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMessage: TextView = view.chat_user_message
        val chatUserMessageTime: TextView = view.chat_user_message_time
        val blockReceivedMessage: ConstraintLayout = view.block_received_message
        val chatReceivedMessage: TextView = view.chat_received_message
        val chatReceivedMessageTime: TextView = view.chat_received_message_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = mListMessageCache.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mListMessageCache[position].from == CURRENT_UID) {
            holder.blockReceivedMessage.visibility = View.GONE
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.chatUserMessage.text = mListMessageCache[position].text
            holder.chatUserMessageTime.text =
                mListMessageCache[position].timeStamp.toString().asTime()
        } else {
            holder.blockReceivedMessage.visibility = View.VISIBLE
            holder.blockUserMessage.visibility = View.GONE
            holder.chatReceivedMessage.text = mListMessageCache[position].text
            holder.chatReceivedMessageTime.text =
                mListMessageCache[position].timeStamp.toString().asTime()
        }
    }

    fun addItemToBottom(item: CommonModel, onSuccess: () -> Unit){
        if (!mListMessageCache.contains(item)) {
            mListMessageCache.add(item)
            notifyItemInserted(mListMessageCache.size)
        }
    }
    fun addItemToTop(item: CommonModel, onSuccess: () -> Unit){
        if (!mListMessageCache.contains(item)) {
            mListMessageCache.add(item)
            mListMessageCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
    }
}

