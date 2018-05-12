package com.example.makro123.twitterapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_ticket.view.*
import kotlinx.android.synthetic.main.ads_ticket.view.*
import kotlinx.android.synthetic.main.twetts_ticket.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    var listOfTweets = ArrayList<Ticket>()
    var adapter: MyTweetsAdpater? = null
    var myEmail: String? = null
    var userUID: String? = null

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gets data from other activity
        try {
            val bundle: Bundle = intent.extras
            myEmail = bundle.getString("email")
            userUID = bundle.getString("uid")
        } catch (ex: Exception) {
            myEmail = "test@gmail.com"
            userUID = "hsafhoisafo0183012jfhno1"
        }

        // Dummy Data
        listOfTweets.add(Ticket("0", "him", "url", "add"))

        adapter = MyTweetsAdpater(this, listOfTweets)
        lvTweets.adapter = adapter

        loadPost()
    }

    inner class MyTweetsAdpater : BaseAdapter {

        var listTweetsAdpater = ArrayList<Ticket>()
        var context: Context? = null

        constructor(context: Context, listNotesAdpater: ArrayList<Ticket>) : super() {
            this.listTweetsAdpater = listNotesAdpater
            this.context = context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val myTweet = listTweetsAdpater[p0]

            if (myTweet.tweetPersonUID.equals("add")) {
                // Load "add" Ticket
                val myView = layoutInflater.inflate(R.layout.add_ticket, null)

                myView.iv_attach.setOnClickListener {
                    loadImage()
                }

                myView.iv_post.setOnClickListener {
                    // Upload to the server
                    myRef.child("posts").push().setValue(PostInfo(
                            userUID!!,
                            myView.etPost.text.toString(),
                            downloadUrl!!
                    ))
                    myView.etPost.setText("")
                }

                return myView

            } else if (myTweet.tweetPersonUID.equals("loading")) {
                // Call loading screen when needed
                val myView = layoutInflater.inflate(R.layout.laoding_ticket, null)

                return myView

            } else if (myTweet.tweetPersonUID.equals("ads")) {
                // Read "add_ticket" layout
                val myView = layoutInflater.inflate(R.layout.ads_ticket, null)

                // Load new ads
                val mAdView = myView.adView
                val adRequest = AdRequest.Builder().build()
                mAdView.loadAd(adRequest)

                return myView

            } else {
                // Load "Tweet" ticket
                val myView = layoutInflater.inflate(R.layout.twetts_ticket, null)

                // Set values to layout
                myView.txt_tweet.text = myTweet.tweetText


                // Put a picture in Image View with picasso plugin
                Picasso.get().load(myTweet.tweetImageUrl).into(myView.tweet_picture)

                myRef.child("Users").child(myTweet.tweetPersonUID)
                        .addValueEventListener(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError?) {}

                            override fun onDataChange(dataSnapshot: DataSnapshot?) {

                                try {

                                    val td = dataSnapshot!!.value as HashMap<String, Any>

                                    for (key in td.keys) {
                                        val userInfo = td[key] as String

                                        if (key.equals("ProfileImage")) {
                                            Picasso.get().load(userInfo).into(myView.picture_path)
                                        } else {
                                            myView.txtUserName.text = userInfo
                                        }
                                    }
                                    adapter!!.notifyDataSetChanged()

                                } catch (ex: Exception) {}
                            }

                        })

                return myView
            }
        }

        override fun getItem(p0: Int): Any {
            return listTweetsAdpater[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {

            return listTweetsAdpater.size

        }
    }

    val PICK_IMAGE_CODE = 123
    fun loadImage() {

        val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_CODE)
    }

    // Run after "startActivityForResult" finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE && data != null && resultCode == RESULT_OK) {

            val selectedImage = data.data
            val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage, filePathColum, null, null, null)
            cursor.moveToFirst()
            val coulomIndex = cursor.getColumnIndex(filePathColum[0])
            val picturePath = cursor.getString(coulomIndex)
            cursor.close()
            UploadImage(BitmapFactory.decodeFile(picturePath))
        }
    }

    var downloadUrl: String? = null
    fun UploadImage(bitmap: Bitmap) {
        listOfTweets.add(0, Ticket("0", "him", "url", "loading"))
        adapter!!.notifyDataSetChanged()

        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://twitterapp-f963c.appspot.com/")
        val df = SimpleDateFormat("ddMMyyHHmmss")
        val dataobj = Date()
        val imagePath = SplitString(myEmail!!) + "." + df.format(dataobj) + ".jpg"
        val ImageRef = storgaRef.child("Profile Pictures/" + imagePath)

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = ImageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, "fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { taskSnapshot ->

            downloadUrl = taskSnapshot.downloadUrl!!.toString()
            listOfTweets.removeAt(0)
            adapter!!.notifyDataSetChanged()

        }
    }

    fun SplitString(email: String): String {
        val split = email.split("@")
        return split[0]
    }

    fun loadPost() {

        myRef.child("posts")
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot?) {

                        try {

                            val td = dataSnapshot!!.value as HashMap<String, Any>

                            for (key in td.keys) {
                                val post = td[key] as HashMap<String, Any>

                                listOfTweets.add(Ticket(
                                        key,
                                        post["text"] as String,
                                        post["postImage"] as String,
                                        post["userUID"] as String
                                ))
                            }
                            adapter!!.notifyDataSetChanged()

                        } catch (ex: Exception) {}
                    }

                })
    }

}
