package uz.h1001.hadith.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.h1001.hadith.domain.model.Hadith
import uz.h1001.hadith.domain.model.Response
import uz.h1001.hadith.domain.use_case.GetHadiths
import uz.h1001.hadith.domain.use_case.UseCases
import javax.inject.Inject

@HiltViewModel
class HadithsViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {

    private val _hadithsLiveData = MutableLiveData<Response<List<Hadith>>>(Response.Loading)
    val hadithsLiveData:LiveData<Response<List<Hadith>>> get() = _hadithsLiveData

    private val _searchHadithsLiveData = MutableLiveData<Response<List<Hadith>>>(Response.Loading)
    val searchHadithsLiveData:LiveData<Response<List<Hadith>>> get() = _searchHadithsLiveData

    init {
        getHadiths()
    }

    fun getHadiths(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getHadiths.invoke().collect { response ->
                _hadithsLiveData.postValue(response)
            }
        }
    }

    fun searchHadiths(query:String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchHadiths.invoke(query = query).collect { response ->
                _searchHadithsLiveData.postValue(response)
            }
        }
    }

}