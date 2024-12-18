package com.oztasibrahimomer.chatapp.feature.auth.signIn

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.format.SignStyle
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()



    fun signIn(email:String,password:String) {
        _state.value = SignInState.Loading


        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            _state.value = SignInState.Success

        }.addOnFailureListener {
            _state.value=SignInState.Error

        }



    }

    fun signOut() {
        auth.signOut()

    }


}





sealed class SignInState {

    data object Nothing : SignInState()
    data object Loading : SignInState()
    data object Error : SignInState()
    data object Success : SignInState()
}