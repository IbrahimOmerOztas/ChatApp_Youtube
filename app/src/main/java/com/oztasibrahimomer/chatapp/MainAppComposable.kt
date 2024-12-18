package com.oztasibrahimomer.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.oztasibrahimomer.chatapp.feature.auth.signIn.SignInScreen
import com.oztasibrahimomer.chatapp.feature.home.HomeScreen
import javax.inject.Inject


@Composable
fun MainApp() {

    Surface(
        modifier=Modifier.fillMaxSize()
    ){
        val currentuser= FirebaseAuth.getInstance().currentUser
        val navController = rememberNavController()
        val start = if(currentuser!=null) "home" else "login"

        NavHost(navController = navController, startDestination = start){

            composable("login"){
                SignInScreen(navController)
            }
            composable("home"){
                HomeScreen(navController = navController)
            }




        }



    }



}