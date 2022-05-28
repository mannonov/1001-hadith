package uz.h1001.hadith.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.h1001.hadith.data.database.HadithDatabase
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase

@Dao
interface HadithDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHadiths(list: List<HadithModelDatabase>)

    @Query("SELECT * FROM hadith_table")
    suspend fun getHadiths(): List<HadithModelDatabase>

    @Query("SELECT * FROM hadith_table WHERE number LIKE :query OR title LIKE :query")
    suspend fun searchHadithFromDatabase(query: String): List<HadithModelDatabase>

}