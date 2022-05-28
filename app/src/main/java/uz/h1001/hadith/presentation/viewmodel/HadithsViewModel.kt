package uz.h1001.hadith.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.h1001.hadith.core.Constants
import uz.h1001.hadith.core.SingleMapper
import uz.h1001.hadith.data.database.HadithDatabase
import uz.h1001.hadith.data.database.entitiy.HadithModelDatabase
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import uz.h1001.hadith.domain.use_case.GetHadiths
import uz.h1001.hadith.domain.use_case.HadithDataStore
import uz.h1001.hadith.domain.use_case.UseCases
import javax.inject.Inject

@HiltViewModel
class HadithsViewModel @Inject constructor(
    private val useCases: UseCases,
    private val database: HadithDatabase,
    private val dataStore: HadithDataStore,
    private val mapper: SingleMapper<Hadith, HadithModelDatabase>
) : ViewModel() {

    private val _hadithsLiveData = MutableLiveData<Response<List<Hadith>>>(Response.Loading)
    val hadithsLiveData: LiveData<Response<List<Hadith>>> get() = _hadithsLiveData

    private val _searchHadithsLiveData = MutableLiveData<Response<List<Hadith>>>(Response.Loading)
    val searchHadithsLiveData: LiveData<Response<List<Hadith>>> get() = _searchHadithsLiveData

    init {
        getHadiths()
        Log.d(Constants.TAG, "ViewModelCreated")
    }

    fun getHadiths() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(Constants.TAG, "getHadiths: database version remote config ${useCases.getDatabaseVersion()}")
            Log.d(Constants.TAG, "getHadiths: database version local ${dataStore.getLocalDatabaseVersion()}")
            if (useCases.getDatabaseVersion() != dataStore.getLocalDatabaseVersion()) {
                useCases.getHadiths.invoke().collect { response ->
                    when (response) {
                        is Response.Success -> {
                            _hadithsLiveData.postValue(response)
                            database.hadithDao().insertHadiths(response.data.map { mapper(it) })
                            dataStore.saveLocalDatabaseVersion(useCases.getDatabaseVersion())
                        }
                        else -> {
                            _hadithsLiveData.postValue(response)
                        }
                    }

                }
            } else {
                _hadithsLiveData.postValue(
                    Response.Success(
                        database.hadithDao().getHadiths().map {
                            Hadith(
                                number = it.number,
                                title = it.title,
                                description = it.description
                            )
                        })
                )
            }
        }
    }

    fun searchHadiths(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchHadiths.invoke(query = query).collect { response ->
                _searchHadithsLiveData.postValue(response)
            }
        }
    }

}