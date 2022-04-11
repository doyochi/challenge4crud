package id.hikmah.binar.challenge4crud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.hikmah.binar.challenge4crud.model.User

@Database(entities = [User::class], version = 1)
abstract class NoteData : RoomDatabase() {
//    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: NoteData? = null

        fun getInstance(context: Context): NoteData? {
            if (INSTANCE == null) {
                synchronized(NoteData::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NoteData::class.java, "NoteData.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
