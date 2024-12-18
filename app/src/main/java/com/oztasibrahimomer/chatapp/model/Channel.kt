package com.oztasibrahimomer.chatapp.model

data class Channel(

    val key: String ="",
    val data: String="",
    val createdAt: Long = System.currentTimeMillis()
)
