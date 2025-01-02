package com.oztasibrahimomer.chatapp.feature.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oztasibrahimomer.chatapp.model.Message

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavController,
    channelId:String,
    viewModel: ChatViewModel = hiltViewModel()

) {

    val messages = viewModel.messages.collectAsState()

    LaunchedEffect(key1 = true){
        viewModel.listenForMessages(channelId)
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ){

            ChatMessages(
                messages = messages.value,
                onSendMessage ={idd->
                    viewModel.sendMessage(idd,channelId)
                } )
        }
    }





}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatMessages(
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
) {

    val msg = remember{ mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .border(2.dp,Color.Red)
        .padding()){
        LazyColumn{
            items(messages){ message->

                ChatBubble(message=message)

            }
        }

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(color = Color.White),
            verticalAlignment = Alignment.Bottom
        ){
            TextField(
                value = msg.value,
                onValueChange ={msg.value=it},
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFF99EAF5), RoundedCornerShape(16.dp)),
                trailingIcon = {
                    IconButton(onClick = {
                        onSendMessage(msg.value)
                        msg.value=""
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "", tint = Color(
                            0xFF104E57
                        )
                        )
                    }
                },
                singleLine = true,
                maxLines = 1,
                placeholder = {
                    Text(text = "Send Message", fontSize = 11.sp, color = Color.Black)
                }

                )

        }
    }

}

@Composable
fun ChatBubble(
    message:Message
) {

    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser){
        Color(0xFF15CEE6)
    }
    else{
        Color(0xFFBBF5FD)
    }

    Box(
        modifier= Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        val alignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart


        Box(
            modifier = Modifier
                .padding(4.dp)
                .align(alignment)
                .height(40.dp)
                .background(color = bubbleColor, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = message.message,
                color=Color.Black,
                modifier = Modifier.padding(4.dp))
            }


    }

}