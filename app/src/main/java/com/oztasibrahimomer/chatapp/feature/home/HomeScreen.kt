package com.oztasibrahimomer.chatapp.feature.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oztasibrahimomer.chatapp.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel= hiltViewModel()
) {

    val context= LocalContext.current
    val isAddChannelDialog = remember{ mutableStateOf(false) }
    val channels = viewModel.channels.collectAsState()
    val counter = remember{ mutableStateOf(0) }

    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = {
                          isAddChannelDialog.value=true
                },
                colors = IconButtonDefaults.iconButtonColors(Color(0xFF7AE1EE)),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(55.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_person), contentDescription = "")

            }
        }
    ){

        if(isAddChannelDialog.value){
            AddChannelDialog(isAddChannelDialog,onAddChannel = {name->
                viewModel.addChannel(name)
            })
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            ) {
            if (channels.value.isEmpty()) {
                Text("No channels available")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {


                    items(channels.value){channel->


                        Column{

                            Text(
                                text = channel.data,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF91D9E2))
                                    .clickable {
                                        //navController.navigate("chat")
                                    }
                                    .padding(12.dp)



                            )
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChannelDialog(
    isAddingChannelDialog:MutableState<Boolean>,
    onAddChannel:(String) -> Unit
) {

    val channelName = remember{ mutableStateOf("") }





    AlertDialog(
        onDismissRequest = {
                           isAddingChannelDialog.value = !isAddingChannelDialog.value
        },
        confirmButton = {

            Button(
                onClick = {
                    onAddChannel(channelName.value)
                    isAddingChannelDialog.value = !isAddingChannelDialog.value},
                modifier=Modifier.fillMaxWidth()

            ) {
                Text(text = "Add")
            }

        },
        title = {
            Text(text = "Add Channel")
        },
        text = {


            Column(
                modifier=Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){

                TextField(
                    value = channelName.value,
                    onValueChange ={channelName.value = it},
                    label = { Text(text = "Channel Name")},
                    singleLine = true)

                Spacer(modifier = Modifier.height(8.dp))



            }

        }
    )

    /*Column(
        modifier=Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(text = "Add Channel")
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = channelName.value,
            onValueChange ={channelName.value = it},
            label = { Text(text = "Channel Name")},
            singleLine = true)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {onAddChannel(channelName.value)},
            modifier=Modifier.fillMaxWidth()

        ) {
            Text(text = "Add")
        }

        Button(
            onClick = {

            },
            modifier=Modifier.fillMaxWidth()

        ) {
            Text(text = "kapat")
        }


    }*/

}
