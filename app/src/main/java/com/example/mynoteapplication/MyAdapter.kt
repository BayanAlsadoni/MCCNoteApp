package com.example.mynoteapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class MyAdapter(val context:Context, val data : ArrayList<ItemData>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.imageView2)
        val txt = itemView.findViewById<TextView>(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(context)
            .inflate(R.layout.item,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt.text = data[position].name
        Picasso.get().load(data[position].image).into(holder.image)
        val id = 0
        holder.image.setOnClickListener {
            Home.clickTrack()
            if(context is Home){
                val i = Intent(context, Notes::class.java)
                i.putExtra("name",data[position].name)
                i.putExtra("image",data[position].image)
                if (data[position].id == 1){
                    i.putExtra("id",1)
                }else if (data[position].id == 2){
                    i.putExtra("id",2)
                }else if (data[position].id == 3){
                    i.putExtra("id",3)
                }

                context.startActivity(i)
            }else if (context is Notes){

                val db = Firebase.firestore

                val i = Intent(context, Detailes::class.java)
                i.putExtra("name",data[position].name)
                i.putExtra("image",data[position].image)
                i.putExtra("id",data[position].id)
//                db.collection("")

                if (data[position].id == 1){
                    i.putExtra("id",1)
                }else if (data[position].id == 2){
                    i.putExtra("id",2)
                }else if (data[position].id == 3){
                    i.putExtra("id",3)
                }else if (data[position].id == 5){
                    i.putExtra("id",5)
                }else if (data[position].id == 6){
                    i.putExtra("id",6)
                }else if (data[position].id == 4){
                    i.putExtra("id",4)
                }


                context.startActivity(i)
            }
            Toast.makeText(context, "image clicked", Toast.LENGTH_SHORT).show()
        }
    }
}

data class ItemData(val name:String,val image:String,val id:Int)