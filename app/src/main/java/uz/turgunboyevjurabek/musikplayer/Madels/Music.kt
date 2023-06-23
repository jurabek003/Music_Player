package uz.turgunboyevjurabek.musikplayer.Madels

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
data class Music(

    var id:Long?,

    var title:String?,
    var imagePath:String?,
    var musicPath:String?,
    var author:String): Serializable
