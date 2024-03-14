package com.example.testfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    lateinit var fullName: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirmPassword: EditText
    lateinit var registerBtn: Button
    lateinit var goToLogin: Button
    var valid = true


    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        fullName = findViewById(R.id.registerName)
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        confirmPassword = findViewById(R.id.confirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        goToLogin = findViewById(R.id.gotoLogin)

        checkField(fullName);
        checkField(email);
        checkField(password);
        checkField(confirmPassword)

        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()

        val intent = Intent(this, LoginActivity::class.java)

        goToLogin.setOnClickListener {
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            try {
                val mEmail = email.text.toString()
                val mPassword = password.text.toString()
                val mConfirmPassword = confirmPassword.text.toString()

                if (mEmail.isNotEmpty() && mPassword.isNotEmpty() && mConfirmPassword.isNotEmpty()) {
                    if (mPassword == mConfirmPassword) {
                        auth.createUserWithEmailAndPassword(mEmail, mPassword)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user: FirebaseUser? = auth.currentUser
                                    val df: DocumentReference =
                                        store.collection("Users").document(user!!.uid)
                                    val userInfo: HashMap<String, Any> = HashMap()
                                    userInfo.put("FullName", fullName.text.toString())
                                    userInfo.put("Email", email.text.toString())

                                    //check if the user is the admin
                                    userInfo.put("isUser", "1")

                                    df.set(userInfo)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Password doesn't match",
                                        Toast.LENGTH_SHORT
                                    )
                                }
                            }
                    } else {
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Empty fields are not allowed !", Toast.LENGTH_SHORT)
                        .show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun checkField(text: EditText): Boolean {
        if (text.text.isEmpty()) {
            text.setError("This field cannot be empty");
            valid = false
        } else {
            valid = true;
        }
        return valid;
    }

}