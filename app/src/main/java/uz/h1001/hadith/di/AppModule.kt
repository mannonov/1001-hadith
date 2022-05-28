package uz.h1001.hadith.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.core.SingleMapper
import uz.h1001.hadith.data.database.HadithDatabase
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase
import uz.h1001.hadith.data.database.mapper.HadithMapper
import uz.h1001.hadith.data.repository.HadithRepositoryImpl
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.repositoy.HadithRepository
import uz.h1001.hadith.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            Log.d(Constants.TAG, "provideRemoteConfig: init")
        }
        return remoteConfig
    }

    @Provides
    fun provideHadithsRef(
        db: FirebaseFirestore
    ) = db.collection(Constants.HADITHS)


    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, Constants.DATA_STORE_NAME)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(Constants.DATA_STORE_NAME) }
        )
    }

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : HadithDatabase{
        return HadithDatabase.getInstance(appContext)
    }

    @Provides
    fun provideHadithMapper() : SingleMapper<Hadith,HadithModelDatabase> = HadithMapper()

    @Provides
    fun provideUseCases(repository: HadithRepository) = UseCases(
        getHadiths = GetHadiths(repository = repository),
        searchHadiths = SearchHadiths(repository = repository),
        getDatabaseVersion = GetDatabaseVersion(repository = repository)
    )

    @Provides
    fun provideHadithsRepository(
        hadithsRef: CollectionReference,
        remoteConfig: FirebaseRemoteConfig,
        database:HadithDatabase
    ): HadithRepository =
        HadithRepositoryImpl(
            hadithReference = hadithsRef,
            remoteConfig = remoteConfig,
            database = database
        )

}