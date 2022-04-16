package com.example.chatKotlin.chat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Model.Message
import com.example.chatKotlin.chat.helper.Preferences

class MessageAdapter(context: Context, objects: ArrayList<Message>) :
    ArrayAdapter<Message>(context, 0, objects) {

    private val messages: ArrayList<Message> = objects
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        if (messages != null) {

            val preferences: Preferences = Preferences(context)
            val idSenderUser = preferences.getIdentification()

            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val message: Message = messages[position]

            if (idSenderUser.equals(message.userId)) {
                view = inflater.inflate(R.layout.item_message_sender, parent, false)
            } else {
                view = inflater.inflate(R.layout.item_message_recipient, parent, false)
            }

            val tvMessage: TextView = view.findViewById(R.id.tv_message)
            tvMessage.text = message.message
        }
        return view!!
    }
}
