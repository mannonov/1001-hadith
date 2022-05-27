package uz.h1001.hadith.domain.use_case

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import uz.h1001.hadith.core.Constants

class HadithDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun saveLocalDatabaseVersion(newVersion: Long) {
        dataStore.edit {
            it[Constants.LOCAL_DATABASE_VERSION] = newVersion
        }
    }

    suspend fun getLocalDatabaseVersion(): Long {
        return dataStore.data.map { preferences ->
                preferences[Constants.LOCAL_DATABASE_VERSION] ?: 0
            }.first()
    }
}