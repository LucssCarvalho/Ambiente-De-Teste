package com.example.chatKotlin.chat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Model.Chat

class ChatAdapter(c: Context, objects: ArrayList<Chat>) :
    ArrayAdapter<Chat>(c, 0, objects) {

    private val chats: ArrayList<Chat> = objects

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null

        if (chats != null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.chat_list, parent, false)
            val nameContact: TextView = view.findViewById(R.id.tv_chat_name)
            val lastMessage: TextView = view.findViewById(R.id.tv_lastMessage)
            val chat: Chat = chats[position]
            nameContact.text = chat.name
            lastMessage.text = chat.lastMessage
        }
        return view!!
    }
}
