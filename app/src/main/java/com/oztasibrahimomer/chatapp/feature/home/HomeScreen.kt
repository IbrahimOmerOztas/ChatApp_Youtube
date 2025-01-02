package com.oztasibrahimomer.chatapp.feature.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oztasibrahimomer.chatapp.R
import com.oztasibrahimomer.chatapp.model.Channel
import com.oztasibrahimomer.chatapp.ui.theme.darkGray

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
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Messages",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.exitApp()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }

                                  },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "",
                            tint = Color.White
                        )

                    }

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(Color(0xFF1C85DA))

            )
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                          isAddChannelDialog.value=true
                },
                colors = IconButtonDefaults.iconButtonColors(Color(0xFF1C85DA)),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(55.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_person),
                    contentDescription = "",
                    tint = Color.White
                )

            }
        },
        containerColor = Color(0xFF595B61)
    ){

        if(isAddChannelDialog.value){
            AddChannelDialog(isAddChannelDialog,onAddChannel = {name->
                viewModel.addChannel(name)
            })
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF7AE1EE), Color(0xFFD7F2F5))
                )
            )
            ) {


            /*Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier= Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF5FDEEE))
                        .height(45.dp)
                        .padding(8.dp),
                    text = "Messages",
                    color = Color(0xFF000000),
                    style = TextStyle(fontSize = 22.sp, fontStyle = FontStyle.Italic))

                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(Color(0xFF000000)),
                    modifier = Modifier.padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "",
                        tint = Color(0xFF000000)
                    )

                }

            }
            Spacer(modifier = Modifier.height(12.dp))*/

            if (channels.value.isEmpty()) {
                Text("No channels available")
            } else {


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                   /* item {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Green)
                                    .height(40.dp)
                                    .padding(8.dp),
                                text = "Messages",
                                color = Color.Red,
                                style = TextStyle(fontSize = 22.sp, fontStyle = FontStyle.Italic))

                        }


                    }*/

                    item {

                        TextField(
                            value ="" ,
                            onValueChange ={},
                            placeholder = { Text(text = "Search....")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color(0xFF7AE1EE))
                                .clip(RoundedCornerShape(20.dp)),
                            textStyle = TextStyle(color=Color.LightGray)
                        )
                    }



                    items(channels.value){channel->


                        Column{

                            ChannelItem(channelName = channel) {

                                navController.navigate("chat/${channel.key}")
                                Toast
                                    .makeText(context, channel.key, Toast.LENGTH_SHORT)
                                    .show()

                            }


                        }
                    }
                }

            }
        }
    }
}


@Composable
fun ChannelItem(
    channelName: Channel,
    onClick : () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF5BFA50))
            .clickable {
                       onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = channelName.data[0].toString(),
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(Color.Yellow.copy(alpha = 0.3f)),
            color=Color.White,
            style = TextStyle(fontSize = 35.sp)
        )
        
        Text(
            text = channelName.data,
            modifier = Modifier
                .padding(8.dp))

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
