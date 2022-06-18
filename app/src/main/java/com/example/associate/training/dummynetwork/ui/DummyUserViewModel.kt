package com.example.associate.training.dummynetwork.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.work.ListenableWorker
import com.example.associate.training.amphibian.network.Amphibian
import com.example.associate.training.amphibian.network.AmphibianApi
import com.example.associate.training.amphibian.ui.AmphibianApiStatus
import com.example.associate.training.dummynetwork.DummyRepository
import com.example.associate.training.dummynetwork.Result
import com.example.associate.training.dummynetwork.data.DummyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DummyUserViewModel : ViewModel() {

    private val _dummyUserList = MutableLiveData<List<DummyData>>()
    val dummyUserList: LiveData<List<DummyData>> = _dummyUserList

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getAmphibianList()
    }

    fun getAmphibianList() {
        viewModelScope.launch {
            val repository = DummyRepository()

            when(val result = repository.makeLoginRequest()) {
                is Result.Success -> {
                    println(result.data)
                    _dummyUserList.value = result.data.data.toList()
                    Log.w("dogtim1", "" + result.data.data.toString())
                }
                is Result.Error -> println(result.exception.message)
                else -> {
                    Log.w("dogtim1", "doNothing")
                }
            }
        }

    }
}
