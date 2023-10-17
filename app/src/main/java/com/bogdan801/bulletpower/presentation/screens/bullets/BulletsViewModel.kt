package com.bogdan801.bulletpower.presentation.screens.bullets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.bulletpower.domain.model.Bullet
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
class BulletsViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel()  {
    private val _screenState = MutableStateFlow(BulletsScreenState())
    val screenState = _screenState.asStateFlow()

    fun addBullet(bullet: Bullet){
        viewModelScope.launch {
            repository.insertBullet(bullet)
        }
    }

    fun editBullet(bullet: Bullet){
        viewModelScope.launch {
            repository.updateBullet(bullet)
        }
    }

    fun deleteBullet(id: Int){
        viewModelScope.launch {
            repository.deleteBullet(id)
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
                val found = repository.searchBullets(searchQuery)
                it.copy(
                    foundItems = found
                )
            }
        }
    }

    private fun updateList(newList: List<Bullet>){
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
            repository.getBullets().collect{ list ->
                updateList(list)
            }
        }
    }
}