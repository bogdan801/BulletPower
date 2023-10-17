package com.bogdan801.bulletpower.presentation.screens.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel()  {
    private val _screenState = MutableStateFlow(DevicesScreenState())
    val screenState = _screenState.asStateFlow()

    fun addDevice(device: Device){
        viewModelScope.launch {
            repository.insertDevice(device)
        }
    }

    fun editDevice(device: Device){
        viewModelScope.launch {
            repository.updateDevice(device)
        }
    }

    fun deleteDevice(id: Int){
        viewModelScope.launch {
            repository.deleteDevice(id)
        }
    }

    fun doSearch(searchQuery: String){
        viewModelScope.launch {
            _screenState.update {
                it.copy(
                    searchQuery = searchQuery
                )
            }
        }

        viewModelScope.launch {
            _screenState.update {
                val found = repository.searchDevices(searchQuery)
                it.copy(
                    foundItems = found
                )
            }
        }
    }

    private fun updateList(newList: List<Device>){
        _screenState.update {
            it.copy(
                items = newList
            )
        }
    }

    fun clearFound(delay: Long = 200){
        runBlocking { delay(delay) }
        _screenState.update {
            it.copy(
                foundItems = listOf()
            )
        }
    }

    init {
        viewModelScope.launch {
            repository.getDevices().collect{ list ->
                updateList(list)
            }
        }
    }
}