package com.example.martin.echo.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.martin.echo.R
import com.example.martin.echo.Songs
import com.example.martin.echo.activities.MainActivity
import com.example.martin.echo.fragments.MainScreen
import com.example.martin.echo.fragments.SongPlaying
import kotlinx.android.synthetic.main.row_custom_mainsreen_adapter.view.*
import java.io.File

class MainScreenAdapter(_songDetails:ArrayList<Songs>,_context: Context): RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>(){
     var songDetails:ArrayList<Songs>?=null
    var mContext:Context?=null
    init {
        this.songDetails=_songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val songObject=songDetails?.get(position)
        if (holder != null) {
            holder.trackTitle?.text=songObject?.songTitle
        }
        holder?.trackArtist?.text=songObject?.artist
    holder?.contentHolder?.setOnClickListener({
        val songPlayingFragment = SongPlaying()
        var args = Bundle()
        args.putString("songArtist",songObject?.artist)
        args.putString("path",songObject?.songData)
        args.putString("songTitle",songObject?.songTitle)
        args.putInt("songId",songObject?.songID?.toInt() as Int)
        args.putInt("songPosition",position)
        args.putParcelableArrayList("songData",songDetails)

        (mContext as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.details_fragment,songPlayingFragment)
                .commit()
    })

    }

    override fun getItemCount(): Int {
         if (songDetails== null){
             return 0
         }else{
             return (songDetails as ArrayList<Songs>).size
         }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.row_custom_mainsreen_adapter,parent,false)
return MyViewHolder(itemView)   }
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        var trackTitle:TextView?=null
        var trackArtist:TextView?=null
        var contentHolder:RelativeLayout?=null
        init {
            trackTitle=view.findViewById(R.id.trackTitle)
            trackArtist=view.findViewById(R.id.trackArtist)
            contentHolder=view.findViewById<RelativeLayout>(R.id.contentRow)

        }
    }




}

