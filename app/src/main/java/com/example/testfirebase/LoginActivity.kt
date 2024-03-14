package com.example.testfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var loginBtn: Button
    lateinit var gotoRegister: Button
    var valid = true
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.loginEmail)
        password = findViewById(R.id.loginPassword)
        loginBtn = findViewById(R.id.loginBtn)
        gotoRegister = findViewById(R.id.gotoRegister)

//        email.setText("dannydanny@gmail.com")

        gotoRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val mEmail = email.text.toString().trim()
            val mPassword = password.text.toString().trim()
            if (mEmail.isNotEmpty() && mPassword.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email.text.toString(), mPassword).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this,"Logined successfully", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                Toast.makeText(this, "Empty fields are not allowed !", Toast.LENGTH_SHORT).show()
            }

        }
    }

//    override fun onStart() {
//        super.onStart()
//        val intent = Intent(this, MainActivity::class.java)
//        if(FirebaseAuth.getInstance().currentUser != null){
//            startActivity(intent)
//        }
//    }
    //for if the user is already logged in
}