package com.oztasibrahimomer.chatapp.feature.auth.signIn

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oztasibrahimomer.chatapp.R
import com.oztasibrahimomer.chatapp.feature.auth.signUp.SignUpDialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    navController: NavController,
    viewModel:SignInViewModel = hiltViewModel()

) {


    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    val signUpDialog = remember{ mutableStateOf(false)}


    if(signUpDialog.value){

       SignUpDialog(signUpDialog,context)
    }

    LaunchedEffect(state.value){
        when(state.value){
            SignInState.Error -> {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()

            }
            SignInState.Success -> {
                navController.navigate("home")

            }
            else->{}
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF7AE1EE), Color(0xFFD7F2F5))
                    )
                )
        )
        {
            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Image(
                    painter = painterResource(id = R.drawable.chatapplogo),
                    contentDescription ="",
                    modifier = Modifier
                        .size(250.dp, 150.dp)
                        .clip(RoundedCornerShape(10.dp))

                )
                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value =email,
                    onValueChange ={email=it},
                    placeholder = { Text(text = "Email", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )

                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value =password,
                    onValueChange ={password=it},
                    placeholder = { Text(text = "Password", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "passwordIcon"
                        )
                    },
                    shape= RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )

                )

                Spacer(modifier = Modifier.height(15.dp))


                if(state.value == SignInState.Loading){
                    CircularProgressIndicator()
                }
                else{
                    Button(
                        onClick = {
                            viewModel.signIn(email,password)


                        },
                        enabled = email.isNotEmpty() && password.isNotEmpty() ,

                        colors = ButtonDefaults.buttonColors(Color(0xFF5C8AFE)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 45.dp)


                    ) {

                        Text(
                            text = "Login",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )

                    }
                }


                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){

                    Text(
                        modifier = Modifier
                            .clickable {
                                       signUpDialog.value=true

                            },
                        text = "No have any account? SignUp",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontSize = 16.sp,
                            color=Color(0xFF5C8AFE)
                        )
                    )


                }

            }
        }


    }

}