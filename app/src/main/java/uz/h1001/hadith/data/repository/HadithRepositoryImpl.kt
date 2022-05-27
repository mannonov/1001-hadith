package uz.h1001.hadith.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.core.SingleMapper
import uz.h1001.hadith.data.database.HadithDatabase
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase
import uz.h1001.hadith.data.database.mapper.HadithMapper
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import uz.h1001.hadith.domain.repositoy.HadithRepository
import uz.h1001.hadith.domain.use_case.HadithDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HadithRepositoryImpl @Inject constructor(
    private val hadithReference: CollectionReference,
    private val remoteConfig: FirebaseRemoteConfig,
    private val hadithDataStore: HadithDataStore,
    private val database: HadithDatabase,
    private val mapper: SingleMapper<Hadith,HadithModelDatabase>
) : HadithRepository {
    override suspend fun getHadithsFromFireStore(): Flow<Response<List<Hadith>>> = callbackFlow {
        if (getDatabaseVersionFromRemoteConfig() != hadithDataStore.getLocalDatabaseVersion()) {
            val snapshotListener =
                hadithReference.orderBy(Constants.NUMBER).addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val response = snapshot.toObjects(Hadith::class.java)
                        Response.Success(response)
                        val insert = database.hadithDao().insertHadiths(response.map { mapper(it!!) })
                    } else {
                        Response.Error(e?.message ?: e.toString())
                    }
                    trySend(response).isSuccess
                }
            awaitClose {
                snapshotListener.remove()
            }
        } else {

        }

    }

    override suspend fun searchHadithsFromFireStore(query: String): Flow<Response<List<Hadith>>> =
        callbackFlow {
            val snapshotListener = hadithReference.whereEqualTo(Constants.TITLE, query)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        Response.Success(snapshot.toObjects(Hadith::class.java))
                    } else {
                        Response.Error(e?.message ?: e.toString())
                    }
                    trySend(response).isSuccess
                }
            awaitClose {
                snapshotListener.remove()
            }
        }

    override fun getDatabaseVersionFromRemoteConfig(): Long {
        return remoteConfig.getLong(Constants.REMOTE_CONFIG_DATABASE_VERSION)
    }
}