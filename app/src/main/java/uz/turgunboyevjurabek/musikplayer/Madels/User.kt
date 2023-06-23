package uz.turgunboyevjurabek.musikplayer.Madels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null

    var  musicPath:Int?=null
}