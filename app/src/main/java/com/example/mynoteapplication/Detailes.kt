package com.example.mynoteapplication

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class Detailes : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var data:ArrayList<ItemData>

    var openTime:Long = 0
    var closeTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailes)
        db = Firebase.firestore
        val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Loading data")

        val noteName = findViewById<TextView>(R.id.noteName)
        val noteDescription = findViewById<TextView>(R.id.noteDescription)
        val noteNumber = findViewById<TextView>(R.id.noteNum)
        val noteImage = findViewById<ImageView>(R.id.noteImage)

        Home.screenTrack("DetailesActivity","Detailes")


        val id = intent.getIntExtra("id",0)

        if(id == 1){
            progressDialog.show()
            db.collection("category").document("5UK9a7OqME4UUpzfIDQS").collection("note").document("tVd11V2VsbgVHftbzrny")
                .get().addOnSuccessListener{

                    noteName.text = it.get("name").toString()
                    noteDescription.text = it.get("description").toString()
                    noteNumber.text = it.get("charNum").toString()
                    Picasso.get().load(it.get("image").toString()).into(noteImage)
                    progressDialog.dismiss()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Log.w("byn", "Error getting documents.", it)
                    Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()

                }

        }else if (id == 2){

            progressDialog.show()
            db.collection("category").document("5UK9a7OqME4UUpzfIDQS").collection("note").document("3zNXAcc0H0SaWuUPc4X0")
                .get().addOnSuccessListener{
                    noteName.text = it.get("name").toString()
                    noteDescription.text = it.get("description").toString()
                    noteNumber.text = it.get("charNum").toString()
                    Picasso.get().load(it.get("image").toString()).into(noteImage)
                    progressDialog.dismiss()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Log.w("byn", "Error getting documents.", it)
                    Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()
                }

        }else if (id == 3){
            progressDialog.show()
            db.collection("category").document("qgJU2cjfkMibdd9CQFlk").collection("note").document("UgbV4yg0dtDpKX5ZA0YJ")
                .get().addOnSuccessListener{
                    noteName.text = it.get("name").toString()
                    noteDescription.text = it.get("description").toString()
                    noteNumber.text = it.get("charNum").toString()
                    Picasso.get().load(it.get("image").toString()).into(noteImage)
                    progressDialog.dismiss()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Log.w("byn", "Error getting documents.", it)
                    Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()
                }

        }else if (id == 4){
            progressDialog.show()
            db.collection("category").document("qgJU2cjfkMibdd9CQFlk").collection("note").document("aBuzbkt5CVamai03KkSh")
                .get().addOnSuccessListener{
                    noteName.text = it.get("name").toString()
                    noteDescription.text = it.get("description").toString()
                    noteNumber.text = it.get("charNum").toString()
                    Picasso.get().load(it.get("image").toString()).into(noteImage)
                    progressDialog.dismiss()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Log.w("byn", "Error getting documents.", it)
                    Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()
                }

        }else if (id == 5){

            progressDialog.show()
            db.collection("category").document("Ntj9CluNlaqugQEQhvFM").collection("note").document("vxq9OFY6nSS9ayJcV6o8")
                .get().addOnSuccessListener{
                    noteName.text = it.get("name").toString()
                    noteDescription.text = it.get("description").toString()
                    noteNumber.text = it.get("charNum").toString()
                    Picasso.get().load(it.get("image").toString()).into(noteImage)
                    progressDialog.dismiss()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Log.w("byn", "Error getting documents.", it)
                    Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()
                }

        }else if (id == 6){
            progressDialog.show()
            db.collection("category").document("Ntj9CluNlaqugQEQhvFM").collection("note").document("hXQvDrRGlJTJu4TxH954")
                .get().addOnSuccessListener{
                    noteName.text = it.get("name").toString()
                    noteDescription.text = it.get("description").toString()
                    noteNumber.text = it.get("charNum").toString()
                    Picasso.get().load(it.get("image").toString()).into(noteImage)
                    progressDialog.dismiss()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Log.w("byn", "Error getting documents.", it)
                    Toast.makeText(this, "falid to get category", Toast.LENGTH_SHORT).show()
                }

        }

    }


    override fun onResume() {
        super.onResume()
        openTime = System.currentTimeMillis()
        Log.e("byn","time to open : $openTime")
    }

    override fun onPause() {
        super.onPause()
        closeTime = System.currentTimeMillis()
        Log.e("byn","time to close : $closeTime")

        val timeSpend = (closeTime - openTime) / 1000
        Log.e("byn","time spend in details = $timeSpend s")

        val time = hashMapOf(
            "pageName" to "Detailes",
            "time" to timeSpend,
            "userId" to "user1"
        )
        db.collection("screenTime").add(time).addOnSuccessListener {
            Log.e("byn","details screen time added successfully")
        }.addOnFailureListener {
            Log.e("byn","failed to add details screen time")
        }

    }


}