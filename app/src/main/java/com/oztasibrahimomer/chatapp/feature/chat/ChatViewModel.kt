package com.oztasibrahimomer.chatapp.feature.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.oztasibrahimomer.chatapp.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase

): ViewModel() {



    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()


    fun listenForMessages(channelId: String) {

        firebaseDatabase.getReference("messages").child(channelId).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {


                }

                override fun onCancelled(error: DatabaseError) {


                }
            })

    }
}