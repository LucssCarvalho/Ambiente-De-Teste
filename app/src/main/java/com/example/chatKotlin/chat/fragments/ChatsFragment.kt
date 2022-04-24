package com.example.chatKotlin.chat.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Activity.ChatActivity
import com.example.chatKotlin.chat.Adapter.ChatAdapter
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils.FIREBASE_TAG_CHATS
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils.FIREBASE_TAG_MESSAGES
import com.example.chatKotlin.chat.Model.Chat
import com.example.chatKotlin.chat.Model.Contact
import com.example.chatKotlin.chat.helper.Base64Custom
import com.example.chatKotlin.chat.helper.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listView: ListView
    private lateinit var chats: ArrayList<Chat>
    private lateinit var adapter: ArrayAdapter<*>
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var valueEventListenerContact: ValueEventListener
    private lateinit var progressBar: ProgressDialog

    companion object {
        const val CONTACT_NAME = "contact_name"
        const val CONTACT_EMAIL = "contact_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseReference.addValueEventListener(valueEventListenerContact)
    }

    override fun onStop() {
        super.onStop()
        firebaseReference.removeEventListener(valueEventListenerContact)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressBar = ProgressDialog(requireActivity()).apply {
            setTitle("LOADING DATA")
            setMessage("wait a moment")
        }

        progressBar.show()

        chats = ArrayList()

        val view: View = inflater.inflate(R.layout.fragment_chats, container, false)

        listView = view.findViewById(R.id.lv_chats)

        adapter = ChatAdapter(requireActivity(), chats)

        listView.adapter = adapter

        val preferences = Preferences(requireActivity())
        val userIdCurrentUser: String = preferences.getIdentification().toString()

        firebaseReference = FirebaseConfig
            .getDatabaseReference()
            .child(FIREBASE_TAG_CHATS)
            .child(userIdCurrentUser)

        valueEventListenerContact = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chats.clear()
                for (data: DataSnapshot in snapshot.children) {
                    val chat: Chat = data.getValue(Chat::class.java) as Chat
                    chats.add(chat)
                }
                adapter.notifyDataSetChanged()
                progressBar.dismiss();
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(requireActivity(), ChatActivity::class.java)
                val chat: Chat = chats[position]
                val email = Base64Custom().decodeBase64(chat.userId)
                intent.putExtra(ContactsFragment.CONTACT_NAME, chat.name)
                intent.putExtra(ContactsFragment.CONTACT_EMAIL, email)
                intent.putExtra(ContactsFragment.CONTACT_STATUS, chat.status)
                startActivity(intent)
            }

        return view
    }
}