package com.example.mynoteapplication

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.ArrayList

class Home : AppCompatActivity() {
     var openTime:Long = 0
     var closeTime:Long = 0
    lateinit var db : FirebaseFirestore
    lateinit var storage: FirebaseStorage

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sdf = SimpleDateFormat("hh:mm:ss")
//        openDate = sdf.format(Date())
//        Log.e("byn1",openDate)
//        println(openDate)

       val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Loading")

        val homeRecycler = findViewById<RecyclerView>(R.id.homeRecyler)

         db = Firebase.firestore
         storage= Firebase.storage
        var reference: StorageReference = storage.reference

        val data = ArrayList<ItemData>()
//        val adapter = MyAdapter(this, data)

        progressDialog.show()
       val category =  db.collection("category")
        category.get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val d = document.data
                    val cat = ItemData(d.get("name").toString(),
                        d.get("image").toString(),
                        d.get("id").toString().toInt())

                    data.add(cat)
                }
                progressDialog.dismiss()
                val adapter = MyAdapter(this, data)

                homeRecycler.adapter = adapter
                homeRecycler.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                progressDialog.dismiss()
                Log.w("byn", "Error getting documents.", exception)
                Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()
            }

screenTrack("HomeActivity","Home")
clickTrack()




    }

companion object{
    val analytics = Firebase.analytics
    fun screenTrack(screenClass:String, screenName:String){


        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_CLASS,screenClass)
            param(FirebaseAnalytics.Param.SCREEN_NAME,screenName)
        }
    }

    fun clickTrack(){
        analytics.logEvent("clickTracking"){
            param("makeTrack","click")

        }
    }

}

    override fun onResume() {
        super.onResume()
         openTime = System.currentTimeMillis()
        Log.e("byn","time to open : "+openTime.toString())
    }

    override fun onPause() {
        super.onPause()
         closeTime = System.currentTimeMillis()
        Log.e("byn","time to close : "+ closeTime.toString())

        val timeSpend = (closeTime - openTime) / 1000
//        Log.e("byn","time spend = ${timeSpend/1000} s")
        Log.e("byn","time spend in home = ${timeSpend} s")

        val time = hashMapOf(
            "pageName" to "Home",
            "time" to timeSpend,
            "userId" to "user1"
        )
        db.collection("screenTime").add(time).addOnSuccessListener {
            Log.e("byn","home screen time added successfully")
        }.addOnFailureListener {
            Log.e("byn","failed to add home screen time")
        }

    }

}