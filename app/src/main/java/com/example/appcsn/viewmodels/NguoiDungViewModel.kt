package com.example.appcsn.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class NguoiDungViewModel: ViewModel() {
    val auth = Firebase.auth
    var email =
        mutableStateOf("")
    var pass =
        mutableStateOf("")

    fun dangNhap(context: Context) {
        auth.signInWithEmailAndPassword(email.value, pass.value)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Đăng nhập thất bại. ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}