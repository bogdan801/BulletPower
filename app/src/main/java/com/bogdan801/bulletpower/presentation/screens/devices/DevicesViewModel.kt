package com.bogdan801.bulletpower.presentation.screens.devices

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle
): ViewModel()  {
    private val _screenState = MutableStateFlow(DevicesScreenState())
    val screenState = _screenState.asStateFlow()

    fun doSearch(searchQuery: String){
        _screenState.update {
            it.copy(
                searchQuery = searchQuery
            )
        }


    }


}