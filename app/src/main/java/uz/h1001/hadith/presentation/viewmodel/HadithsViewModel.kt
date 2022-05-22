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
import javax.inject.Inject

@HiltViewModel
class HadithsViewModel @Inject constructor(private val getHadithsUseCase: GetHadiths) : ViewModel() {

    private val _hadithsLiveData = MutableLiveData<Response<List<Hadith>>>(Response.Loading)
    val hadithsLiveData:LiveData<Response<List<Hadith>>> get() = _hadithsLiveData

    init {
        getHadiths()
    }

    fun getHadiths(){
        viewModelScope.launch(Dispatchers.IO) {
            getHadithsUseCase.invoke().collect { response ->
                _hadithsLiveData.postValue(response)
            }
        }
    }

}