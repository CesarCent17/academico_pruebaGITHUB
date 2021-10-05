package com.centurion.academico

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    var RC_SIGN_IN = 0
    var btn_login: Button = findViewById(R.id.btn_login)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //btn_login:Button = findViewById(R.id.btn_login)
        googleLogin()
    }
    fun googleLogin(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build())

        btn_login.setOnClickListener{
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "Bienvenido ${user!!.displayName}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Principal::class.java))
                finish()
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(this, "Ocurrio un error ${response!!.error!!.errorCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
