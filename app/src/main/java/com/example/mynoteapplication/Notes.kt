package com.example.mynoteapplication

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class Notes : AppCompatActivity() {
    lateinit var db:FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var data:ArrayList<ItemData>
    lateinit var notesRecycler:RecyclerView
    lateinit var progressDialog:ProgressDialog

    var openTime:Long = 0
    var closeTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        findViewById<RecyclerView>(R.id.notesrecy)

         db = Firebase.firestore
         storage= Firebase.storage
         reference = storage.reference
        progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Loading")

        Home.screenTrack("NotesActivity","Notes")

         notesRecycler = findViewById<RecyclerView>(R.id.notesrecy)
         data = ArrayList<ItemData>()
        val id = intent.getIntExtra("id",0)
        if(id == 1){
            progressDialog.show()
            categoryNotes("5UK9a7OqME4UUpzfIDQS")

        }else if(id == 2){
            progressDialog.show()
            categoryNotes("Ntj9CluNlaqugQEQhvFM")
        }else if(id == 3){
            progressDialog.show()
            categoryNotes("qgJU2cjfkMibdd9CQFlk")

        }
    }

    fun categoryNotes(documentPath:String){
        val category =  db.collection("category")
        category.document(documentPath).collection("note")
            .get().addOnSuccessListener{querySnapshot ->
                for (document in querySnapshot) {
                    val d = document.data
                    val cat = ItemData(d.get("name").toString(),d.get("image").toString(),d.get("id").toString().toInt())

                    data.add(cat)
                }
                val adapter = MyAdapter(this, data)
                progressDialog.dismiss()
                notesRecycler.adapter = adapter
                notesRecycler.layoutManager = LinearLayoutManager(this)
            }.addOnFailureListener {
                progressDialog.dismiss()
                Log.w("byn", "Error getting documents.", it)
                Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()

            }
    }



    override fun onResume() {
        super.onResume()
        openTime = System.currentTimeMillis()
        Log.e("byn","time to open : ${openTime.toString()}")
    }

    override fun onPause() {
        super.onPause()
        closeTime = System.currentTimeMillis()
        Log.e("byn","time to close : ${closeTime.toString()}")
        val timeSpend = (closeTime - openTime) / 1000
        Log.e("byn","time spend in notes = $timeSpend s")

        val time = hashMapOf(
            "pageName" to "Notes",
            "time" to timeSpend,
            "userId" to "user1"
        )
        db.collection("screenTime").add(time).addOnSuccessListener {
            Log.e("byn","note screen time added successfully")
        }.addOnFailureListener {
            Log.e("byn","failed to add notes screen time")
        }

    }


}
