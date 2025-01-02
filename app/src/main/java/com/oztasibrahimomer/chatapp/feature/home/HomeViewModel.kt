package com.oztasibrahimomer.chatapp.feature.home


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.oztasibrahimomer.chatapp.model.Channel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
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

    fun addChannel(name:String){
        //val randomNumber = generateUniqueDigitNumber()
        //val key = name.replace(" ","_")+randomNumber
        val key = firebaseDatabase.getReference("channel").push().key


        firebaseDatabase.getReference("channel").child(key!!).setValue(name).addOnSuccessListener {

            getChannels()


        }.addOnSuccessListener {

        }
    }


    fun exitApp(){

        auth.signOut()

    }


    /*private fun generateUniqueDigitNumber(): Int {
        return Random.nextInt(100000, 1000000) // 100.000 ile 999.999 arasında sayı üretir
    }*/
}
