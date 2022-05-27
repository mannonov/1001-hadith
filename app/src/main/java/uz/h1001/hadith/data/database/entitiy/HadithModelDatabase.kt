package uz.h1001.hadith.data.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadith_table")
data class HadithModelDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int = 0,
    val title: String? = "title",
    val description: String? = "description"
)