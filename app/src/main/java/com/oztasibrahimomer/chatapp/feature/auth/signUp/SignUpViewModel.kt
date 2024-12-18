package com.oztasibrahimomer.chatapp.feature.auth.signUp

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.oztasibrahimomer.chatapp.feature.auth.signIn.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()


    fun signUp(email:String,password:String) {

        _state.value = SignUpState.Loading

        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {


            _state.value = SignUpState.Success

        }.addOnFailureListener {
            _state.value = SignUpState.Error
        }

    }
}


sealed class SignUpState {

    data object Nothing : SignUpState()
    data object Loading : SignUpState()
    data object Error : SignUpState()
    data object Success : SignUpState()
}