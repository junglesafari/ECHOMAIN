package com.example.martin.echo

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.DialogTitle

/**
 * Created by martin on 2/22/2018.
 */
class Songs(var songID:Long,var songTitle: String,var artist:String,var songData:String,var dateAdded:Long): Parcelable
{
    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    override fun describeContents(): Int {
     return 0;
    }


}