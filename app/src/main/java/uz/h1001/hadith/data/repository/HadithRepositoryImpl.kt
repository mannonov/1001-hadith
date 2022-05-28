package uz.h1001.hadith.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.data.database.HadithDatabase
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import uz.h1001.hadith.domain.repositoy.HadithRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HadithRepositoryImpl @Inject constructor(
    private val hadithReference: CollectionReference,
    private val remoteConfig: FirebaseRemoteConfig,
    private val database: HadithDatabase
) : HadithRepository {
    override suspend fun getHadithsFromFireStore(): Flow<Response<List<Hadith>>> = callbackFlow {
        val snapshotListener =
            hadithReference.orderBy(Constants.NUMBER).addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val response = snapshot.toObjects(Hadith::class.java)
                    Log.d(Constants.TAG, "getHadithsFromFireStore: $response")
                    Response.Success(response)
                } else {
                    Log.d(Constants.TAG, "getHadithsFromFireStore: ${e.toString()}")
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun searchHadithsFromDatabase(query: String): Response<List<Hadith>>{
        return Response.Success(database.hadithDao().searchHadithFromDatabase(query = "%$query%").map { mapDatabaseModelToUIModel(value = it) })
    }

    override fun getDatabaseVersionFromRemoteConfig(): Long {
        return remoteConfig.getLong(Constants.REMOTE_CONFIG_DATABASE_VERSION)
    }

    fun mapDatabaseModelToUIModel(value: HadithModelDatabase): Hadith {
        return Hadith(number = value.number, title = value.title, description = value.description)
    }

}