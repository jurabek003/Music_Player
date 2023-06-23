package uz.turgunboyevjurabek.musikplayer.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.turgunboyevjurabek.musikplayer.Madels.User

@Dao
interface Data_InterFase {

    @Insert
    fun insertItem(user: User)

    @Query("select *from User where id = :itemImage")
    fun selectImage(itemImage:Int):User

    @Query("select *from User")
    fun getImage():User

}