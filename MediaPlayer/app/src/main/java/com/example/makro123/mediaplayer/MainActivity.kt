package com.example.makro123.mediaplayer

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.song_ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfSongs = ArrayList<SongInfo>()
    var adapter: MySongAdapter? = null
    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check users needed permission
        checkPermission()

        // Load songs
        loadUrlOnline()

        // Thread for Progress Bar updating
        val mTracking = MySongTrack()
        mTracking.start()
    }

    fun loadUrlOnline() {

        listOfSongs.add(SongInfo("Title1", "Name1", "http://server6.mp3quran.net/thubti/001.mp3"))
        listOfSongs.add(SongInfo("Title2", "Name2", "http://server6.mp3quran.net/thubti/002.mp3"))
        listOfSongs.add(SongInfo("Title3", "Name3", "http://server6.mp3quran.net/thubti/003.mp3"))
        listOfSongs.add(SongInfo("Title4", "Name4", "http://server6.mp3quran.net/thubti/004.mp3"))
        listOfSongs.add(SongInfo("Title5", "Name5", "http://server6.mp3quran.net/thubti/005.mp3"))
    }

    inner class MySongAdapter: BaseAdapter {

        var mListSong = ArrayList<SongInfo>()

        constructor(mListSong: ArrayList<SongInfo>): super() {
            this.mListSong = mListSong
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            // Read "song_ticket" layout and the current song
            val mView = layoutInflater.inflate(R.layout.song_ticket, null)
            val song = this.mListSong[position]

            // Set values to the layout
            mView.tvSongName.text = song.title
            mView.tvAuthor.text = song.author
            mView.tvSongName.text = song.title

            mView.buPlay.setOnClickListener {
                // TODO: Play song
                if (mView.buPlay.text.equals("Stop")) {
                    mp!!.stop()
                    mView.buPlay.text = "Play"
                } else {
                    mp = MediaPlayer()
                    try {
                        mp!!.setDataSource(song.songUrl)
                        mp!!.prepare()
                        mp!!.start()
                        mView.buPlay.text = "Stop"
                        sbProgress.max = mp!!.duration

                    } catch (ex: Exception) {}
                }
            }

            return mView
        }

        override fun getItem(position: Int): Any {
            return this.mListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return mListSong.size
        }
    }

    inner class MySongTrack: Thread() {

        override fun run() {
            while (true) {
                try {
                    Thread.sleep(500)
                } catch (ex: Exception) {}

                runOnUiThread {
                    if (mp != null)
                        sbProgress.progress = mp!!.currentPosition
                }
            }
        }

    }

    val READIMAGE:Int=253
    fun checkPermission(){

        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE),READIMAGE)
                return
            }
        }

        loadSongs()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            READIMAGE->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadSongs()
                }else{
                    // Permission denied
                    Toast.makeText(applicationContext,"Cannot access your songs",Toast.LENGTH_LONG).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun loadSongs() {

        val allSongsURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val cursor = contentResolver.query(allSongsURI, null, selection, null, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Toast.makeText(this, "Reading Songs", Toast.LENGTH_LONG).show()
                do {
                    // Read song data
                    val songURI = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                    // Write song to ArrayList
                    listOfSongs.add(SongInfo(songName, songAuthor, songURI))

                } while (cursor.moveToNext())
            } else
                Toast.makeText(this, "You don't have songs in your phone", Toast.LENGTH_LONG).show()

            cursor.close()

            // Set adapter
            adapter = MySongAdapter(listOfSongs)
            lvSongList.adapter = adapter
        }

    }

}
