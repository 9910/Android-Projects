package com.example.makro123.tictactoyonline

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize auth
        mAuth = FirebaseAuth.getInstance()
    }

    fun onLogin(view: View) {
        loginToFirebase(etEmail.text.toString(), etPassword.text.toString())
    }

    fun loginToFirebase(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()

                        // Save in database
                        val currentUser = mAuth!!.currentUser
                        if (currentUser != null)
                            myRef.child("Users").child(splitString(currentUser.email.toString())).child("Request").setValue(currentUser.uid)

                        loadMain()
                    } else {
                        Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        loadMain()
    }

    fun loadMain() {
        val currentUser = mAuth!!.currentUser

        if (currentUser != null) {

            // Save in database
            myRef.child("Users").child(currentUser.uid).setValue(currentUser.email)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)
        }
    }

    fun splitString(str: String): String {
        val split = str.split("@")
        return split[0]
    }

}
