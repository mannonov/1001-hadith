package uz.h1001.hadith.data.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadith_table")
data class HadithModelDatabase(
    @PrimaryKey
    val id: String,
    val number: String? = "number",
    val title: String? = "title",
    val description: String? = "description"
)