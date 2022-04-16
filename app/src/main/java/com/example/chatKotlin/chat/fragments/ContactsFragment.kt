package com.example.chatKotlin.chat.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Adapter.ContactAdapter
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.Model.Contact
import com.example.chatKotlin.chat.Activity.ChatActivity
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils.FIREBASE_TAG_CONTACTS
import com.example.chatKotlin.chat.helper.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listView: ListView
    private lateinit var contacts: ArrayList<Contact>
    private lateinit var adapter: ArrayAdapter<*>
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var valueEventListenerContact: ValueEventListener

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contacts = ArrayList()

        val view: View = inflater.inflate(R.layout.fragment_contacts, container, false)

        listView = view.findViewById(R.id.lv_contacts)

        adapter = ContactAdapter(requireActivity(), contacts)

        listView.adapter = adapter

        val preferences = Preferences(requireActivity())
        val userIdCurrentUser: String = preferences.getIdentification().toString()

        firebaseReference = FirebaseConfig
            .getDatabaseReference()
            .child(FIREBASE_TAG_CONTACTS)
            .child(userIdCurrentUser)

        valueEventListenerContact = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contacts.clear()
                for (data: DataSnapshot in snapshot.children) {
                    val contact: Contact = data.getValue(Contact::class.java) as Contact
                    contacts.add(contact)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(requireActivity(), ChatActivity::class.java)
                val contact: Contact = contacts[position]
                intent.putExtra(CONTACT_NAME, contact.name)
                intent.putExtra(CONTACT_EMAIL, contact.email)

                startActivity(intent)
            }

        return view
    }
}