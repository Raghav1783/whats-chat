package com.example.whatschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : AppCompatActivity() {
    private lateinit var Chatrecyclerview: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendbutton:ImageView
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messageList: ArrayList<message>
    private lateinit var mDbRef: DatabaseReference
    var recieverRoom:String?=null
    var senderRoom: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val recieveruid=intent.getStringExtra("uid")
        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=recieveruid+senderuid
        recieverRoom=senderuid+recieveruid

        supportActionBar?.title= name

        Chatrecyclerview=findViewById(R.id.ChatRecyclerView)
        messageBox=findViewById(R.id.Meassage)
        sendbutton=findViewById(R.id.btn_Send)
        messageList=ArrayList()
        messageAdapter=messageAdapter(this,messageList)
        Chatrecyclerview.layoutManager=LinearLayoutManager(this)
        Chatrecyclerview.adapter=messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message=postSnapshot.getValue(message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        sendbutton.setOnClickListener{
            val m= messageBox.text.toString()
            val messageObject=message(m,senderuid)
            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")



        }


    }
}