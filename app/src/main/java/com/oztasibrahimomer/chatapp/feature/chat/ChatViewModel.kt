package com.oztasibrahimomer.chatapp.feature.chat

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase
import com.oztasibrahimomer.chatapp.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase

): ViewModel() {



    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()


    fun sendMessage( messageText:String,channelId: String) {
        val message = Message(
            id=firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser?.uid ?: "",
            message = messageText,
            senderName = Firebase.auth.currentUser?.displayName ?: "",
            senderImage = Firebase.auth.currentUser?.photoUrl.toString(),
            imageUrl = null

        )

        firebaseDatabase.getReference("messages").child(channelId).push().setValue(message)


    }
    fun listenForMessages(channelId: String) {

        firebaseDatabase.getReference("messages").child(channelId).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()

                    snapshot.children.forEach { data->

                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }

                    }

                    _messages.value = list




                }

                override fun onCancelled(error: DatabaseError) {


                }
            })

    }
}