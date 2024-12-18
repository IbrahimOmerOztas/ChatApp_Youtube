package com.oztasibrahimomer.chatapp.feature.home

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

import com.oztasibrahimomer.chatapp.model.Channel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannels()
    }

    private fun getChannels() {


        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()

            it.children.forEach {data->
                val channel = Channel(data.key!!,data.value.toString())
                list.add(channel)
            }
            _channels.value = list
        }


    }
}
