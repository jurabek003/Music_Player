package uz.turgunboyevjurabek.musikplayer.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.turgunboyevjurabek.musikplayer.Madels.User

@Database(entities = [User::class], version = 1)
abstract class DataBase:RoomDatabase() {
    abstract fun userDao():Data_InterFase

    companion object{
        fun newInstens(context: Context):DataBase{
            return Room.databaseBuilder(context,DataBase::class.java,"Music Data")
                .allowMainThreadQueries()
                .build()
        }
    }
}