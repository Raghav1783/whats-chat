package com.example.whatschat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var btnSignUp: Button
    private lateinit var btnLogin: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()
        btnLogin=findViewById(R.id.btnLogin)
        btnSignUp=findViewById(R.id.btnSignup)
        edtEmail=findViewById(R.id.edtEmail)
        edtPassword=findViewById(R.id.edtPassword)

//        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
//        var check=sharedPreference.getString("check","default")
//
//       if(check.equals("true")){
//            val intent= Intent(this@Login,MainActivity::class.java)
//            finish()
//            startActivity(intent)
//        }




        btnSignUp.setOnClickListener{
            val intent=Intent(this,Signup::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password)

        }
    }
    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
                    var editor = sharedPreference.edit()
                    editor.putString("check","true")
                    editor.commit()
                    // Sign in success, update UI with the signed-in user's information
                    val intent= Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }

    }
}