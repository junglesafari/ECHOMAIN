package com.example.martin.echo.fragments


import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.example.martin.echo.CurrentSongHelper
import com.example.martin.echo.R
import com.example.martin.echo.Songs
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class SongPlaying : Fragment() {
var myActivity: Activity?=null
var mediaPlayer:MediaPlayer?=null
    var startTimeText: TextView?=null
    var endTimeText: TextView?=null
    var playpauseImageButton:ImageButton?=null
             var previousImageButton:ImageButton?=null
                     var nextImageButton:ImageButton?=null
    var loopImageButton:ImageButton?=null
    var seekbar:SeekBar?=null
    var songArtistView:TextView?=null
    var songTitleView:TextView?=null
    var shuffelImageButon:ImageButton?=null
    var currentSongHelper:CurrentSongHelper?=null
    var currentPosition: Int=0
    var fetchSongs:ArrayList<Songs>?=null




    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_song_playing, container, false)
   seekbar=view?.findViewById(R.id.seekBar)
        startTimeText=view?.findViewById(R.id.startTime)
        endTimeText=view?.findViewById(R.id.endTime)
        playpauseImageButton=view?.findViewById(R.id.playPauseButton)
        nextImageButton=view?.findViewById(R.id.nextButton)
        previousImageButton=view?.findViewById(R.id.previousButton)
        loopImageButton=view?.findViewById(R.id.loopButton)
        shuffelImageButon=view?.findViewById(R.id.shuffleButton)
        songArtistView=view?.findViewById(R.id.songArtist)
        songTitleView=view?.findViewById(R.id.songTitle)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myActivity = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var path:String?=null
        var songArtist:String?=null
        var songTitle:String?=null
        var songId:Long=0
        currentSongHelper= CurrentSongHelper()
        currentSongHelper?.isPlaying=true
        currentSongHelper?.isLoop=false
        currentSongHelper?.isShuffle=false
        currentSongHelper= CurrentSongHelper()


        try {

       path=arguments.getString("path")
            songArtist=arguments.getString("songArtist")
            songTitle=arguments.getString("songTitle")
            songId=arguments.getInt("songId").toLong()
            currentPosition=arguments.getInt("songPosition")
            fetchSongs=arguments.getParcelableArrayList("songData")
            currentSongHelper?.songPath=path
            currentSongHelper?.songArtist=songArtist
            currentSongHelper?.songId=songId
            currentSongHelper?.songTitle=songTitle
            currentSongHelper?.currentPosition=currentPosition

        }

        catch (e: Exception){
              e.printStackTrace()
        }
        mediaPlayer= MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
         mediaPlayer?.setDataSource(myActivity, Uri.parse("path"))
            mediaPlayer?.prepare()
        }catch (e:Exception){
       e.printStackTrace()
        }
        mediaPlayer?.start()
        if(currentSongHelper?.isPlaying as Boolean){
            playpauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
        }else{
            playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)
        }
    }
    fun clickHandler(){
        shuffelImageButon?.setOnClickListener({

        })
        nextImageButton?.setOnClickListener({
           currentSongHelper?.isPlaying=true
            if(currentSongHelper?.isShuffle as Boolean){
                playNext("PlayNextLikeNormalShuffel")
            }
            else{
                playNext("PlayNextNormal")
            }
        })
        previousImageButton?.setOnClickListener({
currentSongHelper?.isPlaying=true
            if (currentSongHelper?.isLoop as Boolean){
                loopImageButton?.setBackgroundResource(R.drawable.loop_white_icon)
            }
            playPrevious()
        })
        loopImageButton?.setOnClickListener({
       if(currentSongHelper?.isLoop as Boolean){
           currentSongHelper?.isLoop=false
           loopImageButton?.setBackgroundResource(R.drawable.loop_white_icon)
       }else{
           currentSongHelper?.isLoop=true
           currentSongHelper?.isShuffle=false
           loopImageButton?.setBackgroundResource(R.drawable.loop_icon)
           shuffelImageButon?.setBackgroundResource(R.drawable.loop_white_icon)
       }
        })
        playpauseImageButton?.setOnClickListener({
            if(mediaPlayer?.isPlaying as Boolean){
                mediaPlayer?.pause()
                playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)
             currentSongHelper?.isPlaying=false
            }else{
                mediaPlayer?.start()
                playpauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
                currentSongHelper?.isPlaying=true
            }
        })
    }
    fun playNext(check: String){
        if(check.equals("PlayNextNormal",true)){
            currentPosition=currentPosition+1
        }else if(check.equals("PlayNextLikeNormalShuffel",true)){
            var randomObject=Random()
            var randomPosition=randomObject.nextInt(fetchSongs?.size?.plus(1)as Int)
            currentPosition=randomPosition

        }
        if(currentPosition==fetchSongs?.size){
            currentPosition=0
        }
        currentSongHelper?.isLoop=false
     var nextSong=fetchSongs?.get(currentPosition)
        currentSongHelper?.songTitle=nextSong?.songTitle
        currentSongHelper?.songPath=nextSong?.songData
        currentSongHelper?.currentPosition=currentPosition
        currentSongHelper?.songId=nextSong?.songID as Long
        mediaPlayer?.reset()
        try{
            mediaPlayer?.setDataSource(myActivity,Uri.parse(currentSongHelper?.songPath))
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

        }
    fun playPrevious(){
        currentPosition=currentPosition-1
        if(currentPosition==-1){
            currentPosition=0}

            if (currentSongHelper?.isPlaying as Boolean){
                playpauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
            }else{
                playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)
            }
        currentSongHelper?.isLoop=false
        var nextSong=fetchSongs?.get(currentPosition)
        currentSongHelper?.songTitle=nextSong?.songTitle
        currentSongHelper?.songPath=nextSong?.songData
        currentSongHelper?.currentPosition=currentPosition
        currentSongHelper?.songId=nextSong?.songID as Long
        mediaPlayer?.reset()
        try{
            mediaPlayer?.setDataSource(myActivity,Uri.parse(currentSongHelper?.songPath))
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    }


// Required empty public constructor
