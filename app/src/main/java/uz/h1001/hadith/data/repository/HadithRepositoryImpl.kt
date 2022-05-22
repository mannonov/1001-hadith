package uz.h1001.hadith.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import uz.h1001.hadith.domain.repositoy.HadithRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HadithRepositoryImpl @Inject constructor(private val hadithReference: CollectionReference) : HadithRepository {
    override fun getHadithsFromFireStore(): Flow<Response<List<Hadith>>> = callbackFlow {
        val snapshotListener = hadithReference.orderBy(Constants.NUMBER).addSnapshotListener{snapshot,e ->
            val response = if (snapshot != null){
                Response.Success(snapshot.toObjects(Hadith::class.java))
            }else{
                Response.Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}