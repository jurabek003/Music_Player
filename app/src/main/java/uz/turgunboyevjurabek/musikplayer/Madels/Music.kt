package uz.turgunboyevjurabek.musikplayer.Madels

import java.io.Serializable

data class Music(
    var id:Long?,
    var title:String?,
    var imagePath:String?,
    var musicPath:String?,
    var author:String): Serializable
