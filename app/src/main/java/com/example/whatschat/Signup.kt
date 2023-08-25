package com.example.whatschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnsignin: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var mDBRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mAuth=FirebaseAuth.getInstance()
        btnsignin=findViewById(R.id.btnsignin)
        edtEmail=findViewById(R.id.edtEmail)
        edtPassword=findViewById(R.id.edtPassword)
        edtName=findViewById(R.id.edtName)
            btnsignin.setOnClickListener {
                val email=edtEmail.text.toString()
                val password=edtPassword.text.toString()
                val name=edtName.text.toString()
                signUp(name,email,password)

            }


    }
    fun signUp(name:String,email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@Signup) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email, mAuth.currentUser?.uid!!)
                    val intent= Intent(this@Signup,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Signup,"Some error occured",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
             mDBRef=FirebaseDatabase.getInstance().getReference()
             mDBRef.child("user").child(uid).setValue(user(name,email,uid))


    }
}