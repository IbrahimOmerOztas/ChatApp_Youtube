package com.oztasibrahimomer.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.oztasibrahimomer.chatapp.feature.auth.signIn.SignInScreen
import com.oztasibrahimomer.chatapp.feature.chat.ChatScreen
import com.oztasibrahimomer.chatapp.feature.home.HomeScreen


@Composable
fun MainApp() {

    Surface(
        modifier=Modifier.fillMaxSize()
    ){
        val currentUser= FirebaseAuth.getInstance().currentUser
        val navController = rememberNavController()
        val start = if(currentUser!=null) "home" else "login"

        NavHost(navController = navController, startDestination = start){

            composable("login"){
                SignInScreen(navController)
            }
            composable("home"){
                HomeScreen(navController = navController)
            }
            composable("chat/{channelId}", arguments = listOf(
                navArgument("channelId"){
                    type = NavType.StringType
                }
            )){
                val channelId = it.arguments?.getString("channelId") ?: ""

                ChatScreen(navController = navController, channelId =channelId)
            }




        }



    }



}