package uz.h1001.hadith.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.data.repository.HadithRepositoryImpl
import uz.h1001.hadith.domain.repositoy.HadithRepository
import uz.h1001.hadith.domain.use_case.GetHadiths
import uz.h1001.hadith.domain.use_case.SearchHadiths
import uz.h1001.hadith.domain.use_case.UseCases

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideHadithsRef(
        db: FirebaseFirestore
    ) = db.collection(Constants.HADITHS)

    @Provides
    fun provideUseCases(repository: HadithRepository) = UseCases(
        getHadiths = GetHadiths(repository = repository),
        searchHadiths = SearchHadiths(repository = repository)
    )

    @Provides
    fun provideHadithsRepository(hadithsRef: CollectionReference): HadithRepository =
        HadithRepositoryImpl(hadithReference = hadithsRef)

}