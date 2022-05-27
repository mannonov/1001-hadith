package uz.h1001.hadith.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.h1001.hadith.data.database.dao.HadithDao
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase

@Database(entities = [HadithModelDatabase::class], exportSchema = false, version = 1)
abstract class HadithDatabase : RoomDatabase() {

    abstract fun hadithDao(): HadithDao

    companion object {
        @Volatile
        var INSTANCE: HadithDatabase? = null
        fun getInstance(context: Context): HadithDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HadithDatabase::class.java,
                        "hadith-database"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}