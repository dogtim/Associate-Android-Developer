package com.example.associate.training.dummynetwork.ui

import androidx.lifecycle.*
import com.example.associate.training.dummynetwork.DummyRepository
import com.example.associate.training.dummynetwork.Result
import com.example.associate.training.dummynetwork.data.DummyData
import kotlinx.coroutines.launch

class DummyUserViewModel : ViewModel() {

    private val _dummyUserList = MutableLiveData<List<DummyData>>()
    val dummyUserList: LiveData<List<DummyData>> = _dummyUserList

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDummyUserList()
    }

    private fun getDummyUserList() {
        viewModelScope.launch {
            val repository = DummyRepository()

            when(val result = repository.makeLoginRequest()) {
                is Result.Success -> {
                    _dummyUserList.value = result.data.data.toList()
                }
                is Result.Error -> {
                    // TODO We should have Error handling
                    println(result.exception.message)
                }
            }
        }

    }
}
