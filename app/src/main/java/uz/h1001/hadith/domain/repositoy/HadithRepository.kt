package uz.h1001.hadith.domain.repositoy

import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface HadithRepository {

    suspend fun getHadithsFromFireStore(): Flow<Response<List<Hadith>>>

    suspend fun searchHadithsFromDatabase(query: String): Flow<Response<List<Hadith>>>

    fun getDatabaseVersionFromRemoteConfig(): Long

}