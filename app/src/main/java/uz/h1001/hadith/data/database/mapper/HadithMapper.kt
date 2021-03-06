package uz.h1001.hadith.data.database.mapper

import uz.h1001.hadith.core.SingleMapper
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase
import uz.h1001.hadith.domain.model.Hadith
import javax.inject.Singleton

@Singleton
class HadithMapper : SingleMapper<Hadith, HadithModelDatabase> {
    override fun invoke(value: Hadith): HadithModelDatabase {
        return HadithModelDatabase(
            id = value.id ?: "",
            number = value.number!!,
            title = value.title,
            description = value.description
        )
    }


}