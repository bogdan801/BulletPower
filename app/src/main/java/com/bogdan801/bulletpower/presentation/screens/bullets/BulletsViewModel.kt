package com.bogdan801.bulletpower.presentation.screens.bullets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BulletsViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle
): ViewModel()  {
    private val _screenState = MutableStateFlow(BulletsScreenState())
    val screenState = _screenState.asStateFlow()
}