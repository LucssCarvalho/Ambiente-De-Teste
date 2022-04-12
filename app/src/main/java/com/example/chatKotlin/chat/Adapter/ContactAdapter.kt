package com.example.chatKotlin.chat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Model.Contact


class ContactAdapter(c: Context, objects: ArrayList<Contact>) :
    ArrayAdapter<Contact>(c, 0, objects) {

    private val contacts: ArrayList<Contact> = objects

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null

        if (contacts != null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.contact_list, parent, false)
            val nameContact: TextView = view.findViewById(R.id.tv_name)
            val contact: Contact = contacts[position]
            nameContact.text = contact.name
        }
        return view!!
    }
}
