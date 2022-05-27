package uz.h1001.hadith.core

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    //App
    const val TAG = "AppTag"

    //Firestore
    const val HADITHS = "hadiths"

    //Placeholders
    const val NUMBER = "number"
    const val TITLE = "title"
    const val DESCRIPTION = "description"

    //RemoteConfig
    const val REMOTE_CONFIG_DATABASE_VERSION = "databaseVersion"

    //DataStore keys
    const val DATA_STORE_NAME = "hadith_settings"
    val LOCAL_DATABASE_VERSION = longPreferencesKey("local_database_version")

}