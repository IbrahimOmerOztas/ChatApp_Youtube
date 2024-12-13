package com.oztasibrahimomer.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oztasibrahimomer.chatapp.feature.auth.signIn.SignInScreen


@Composable
fun MainApp() {

    Surface(
        modifier=Modifier.fillMaxSize()
    ){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login"){

            composable("login"){
                SignInScreen(navController)
            }

        }



    }



}