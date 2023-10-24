package com.bogdan801.bulletpower.presentation.screens.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle
): ViewModel()  {
    private val _screenState = MutableStateFlow(MenuScreenState())
    val screenState = _screenState.asStateFlow()
}