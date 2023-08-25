package com.example.whatschat
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import com.google.firebase.ktx.Firebase

class messageAdapter(val context: Context, val messsageList:ArrayList<message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val item_recieve=1;
    val item_sent=2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view: View= LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return ReceiveViewHolder(view)
        }
        else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage=messsageList[position]
       if(holder.javaClass==SentViewHolder::class.java){

           val viewHolder= holder as SentViewHolder
               holder.sentmessage.text=currentmessage.message



       }
        else{
//           val currentmessage=messsageList[position]
           val viewHolder= holder as ReceiveViewHolder
           holder.recievemessage.text=currentmessage.message

       }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage=messsageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)){
            return  item_sent
        }
        else
            return item_recieve
    }
    override fun getItemCount(): Int {
        return messsageList.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentmessage=itemView.findViewById<TextView>(R.id.txt_sent)

    }
    class ReceiveViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView){
        val recievemessage=itemView.findViewById<TextView>(R.id.txt_recieve)
    }

}