package com.android.myapplication.firebaseui_auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            Toast.makeText(applicationContext,"User already signed in , must sign out first",Toast.LENGTH_SHORT).show()
        }

    }

    fun signIn(view: View) {
        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build()
            /*AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()*/
        )
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),1234)
    }
    fun signOut(view: View) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener{
            Toast.makeText(applicationContext,"Successfully signed out",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1234){
            if(resultCode== Activity.RESULT_OK){
                val response = IdpResponse.fromResultIntent(data)
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(applicationContext,"Successfully signed in",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext,"Unenable to sign in",Toast.LENGTH_SHORT).show()
            }

        }
    }
}
