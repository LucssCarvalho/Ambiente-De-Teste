package com.example.chatKotlin.chat.Adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.chatKotlin.chat.Fragments.ChatsFragment
import com.example.chatKotlin.chat.Fragments.ContactsFragment

class TabAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val titleTabs = listOf("CHATS", "CONTACTS")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> ChatsFragment()
            2 -> ContactsFragment()
            else -> ChatsFragment()
        }
    }

    override fun getCount(): Int {
        return titleTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        Log.e("title tabs: " , position.toString())
        return titleTabs[position]
    }
}