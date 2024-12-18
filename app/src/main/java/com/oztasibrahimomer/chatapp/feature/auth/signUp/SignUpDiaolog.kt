package com.oztasibrahimomer.chatapp.feature.auth.signUp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpDialog(

    signUpDialog: MutableState<Boolean>,
    context:Context,
    viewModel: SignUpViewModel = hiltViewModel()



){


    val state = viewModel.state.collectAsState()
    var name by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var passwordConfirm by remember{ mutableStateOf("") }
    val isMatching = password == passwordConfirm && password.isNotEmpty()






    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        containerColor=Color(0x80FFFFFF),
        onDismissRequest = {
                signUpDialog.value = !signUpDialog.value

        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.signUp(email,password)

                    when(state.value){
                        SignUpState.Error -> {
                            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()

                        }
                        SignUpState.Success -> {
                            signUpDialog.value = !signUpDialog.value

                        }
                        else->{}
                    }



                },
                colors = ButtonDefaults.buttonColors(Color(0xFF58BCE9))
            ) {
                Text(text = "Confirm", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)

            }


        },
        title = {
            Text(text = "Sign Up", fontSize = 22.sp, color = Color(0xFF040607), fontWeight = FontWeight.ExtraBold)
        },

        text = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                TextField(
                    value =name,
                    onValueChange ={name=it},
                    placeholder = { Text(text = "Full Name", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "nameIcon"
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )

                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value =email,
                    onValueChange ={email=it},
                    placeholder = { Text(text = "Email", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )

                )
                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value =password,
                    onValueChange ={password=it},
                    placeholder = { Text(text = "Password", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "passwordIcon"
                        )
                    },
                    shape= RoundedCornerShape(15.dp),
                    isError =password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )

                )
                Spacer(modifier = Modifier.height(3.dp))
                TextField(
                    value =passwordConfirm,
                    onValueChange ={passwordConfirm=it},
                    placeholder = { Text(text = "Confirm Password", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "passwordIcon"
                        )
                    },
                    shape= RoundedCornerShape(12.dp),
                    isError =password.isNotEmpty() && passwordConfirm.isNotEmpty() && password != passwordConfirm ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )

                )


            }
        }
    )



}