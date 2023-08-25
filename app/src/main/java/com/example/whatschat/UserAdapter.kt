package com.example.whatschat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context,val useList:ArrayList<user>):RecyclerView.Adapter<UserAdapter.UserViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currUser=useList[position]
        holder.textname.text=currUser.name
        holder.itemView.setOnClickListener{
            val intent= Intent(context,Chat::class.java)
            intent.putExtra("name",currUser.name)
            intent.putExtra("uid",currUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return useList.size
    }
    class UserViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val textname= itemview.findViewById<TextView>(R.id.txtname)
    }
}