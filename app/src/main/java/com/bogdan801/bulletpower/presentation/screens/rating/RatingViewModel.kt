package com.bogdan801.bulletpower.presentation.screens.rating

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import com.bogdan801.bulletpower.domain.repository.Repository
import com.bogdan801.bulletpower.presentation.components.SortBy
import com.bogdan801.bulletpower.presentation.components.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel
@Inject
constructor(
    private val repository: Repository,
    handle: SavedStateHandle
): ViewModel()  {
    private val _screenState = MutableStateFlow(RatingScreenState())
    val screenState = _screenState.asStateFlow()

    fun doSearch(searchQuery: String) {
        _screenState.update {
            it.copy(
                searchQuery = searchQuery
            )
        }

        viewModelScope.launch {
            if(_screenState.value.isSingleShot){
                _screenState.update {
                    it.copy(
                        foundSingleShots = repository.searchSingleShotRating(
                            searchQuery = searchQuery,
                            listToSearch = _screenState.value.singleShotList
                        )
                    )
                }
            }
            else {
                _screenState.update {
                    it.copy(
                        foundMultipleShots = repository.searchMultipleShotRating(
                            searchQuery = searchQuery,
                            listToSearch = _screenState.value.multipleShotList
                        )
                    )
                }
            }
        }

    }

    private fun sortSingleShotList(list: List<SingleShotRatingItem>) = when(_screenState.value.sortOrder){
        SortOrder.Ascending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedBy { it.device?.name }
                SortBy.DeviceType -> list.sortedBy { it.device?.type }
                SortBy.BulletName -> list.sortedBy { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedBy { it.bullet?.weight }
                SortBy.Caliber -> list.sortedBy { it.device?.caliber }
                SortBy.Speed -> list.sortedBy { it.speed }
                SortBy.Energy -> list.sortedBy { it.energy }
            }
        }
        SortOrder.Descending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedByDescending { it.device?.name }
                SortBy.DeviceType -> list.sortedByDescending { it.device?.type }
                SortBy.BulletName -> list.sortedByDescending { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedByDescending { it.bullet?.weight }
                SortBy.Caliber -> list.sortedByDescending { it.device?.caliber }
                SortBy.Speed -> list.sortedByDescending { it.speed }
                SortBy.Energy -> list.sortedByDescending { it.energy }
            }
        }
    }

    private fun sortMultipleShotList(list: List<MultipleShotRatingItem>) = when(_screenState.value.sortOrder){
        SortOrder.Ascending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedBy { it.device?.name }
                SortBy.DeviceType -> list.sortedBy { it.device?.type }
                SortBy.BulletName -> list.sortedBy { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedBy { it.bullet?.weight }
                SortBy.Caliber -> list.sortedBy { it.device?.caliber }
                SortBy.Speed -> list.sortedBy { it.averageSpeed }
                SortBy.Energy -> list.sortedBy { it.averageEnergy }
            }
        }
        SortOrder.Descending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedByDescending { it.device?.name }
                SortBy.DeviceType -> list.sortedByDescending { it.device?.type }
                SortBy.BulletName -> list.sortedByDescending { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedByDescending { it.bullet?.weight }
                SortBy.Caliber -> list.sortedByDescending { it.device?.caliber }
                SortBy.Speed -> list.sortedByDescending { it.averageSpeed }
                SortBy.Energy -> list.sortedByDescending { it.averageEnergy }
            }
        }
    }

    private fun setIsSingleShot(isSingleShot: Boolean){
        _screenState.update {
            it.copy(
                isSingleShot = isSingleShot
            )
        }
    }

    fun setSortOrder(order: SortOrder){
        _screenState.update {
            it.copy(
                sortOrder = order
            )
        }
        if(_screenState.value.isSingleShot){
            setSingleShotRatingList()
        }
    }

    fun setSortBy(sortBy: SortBy){
        _screenState.update {
            it.copy(
                sortBy = sortBy
            )
        }
        if(_screenState.value.isSingleShot){
            setSingleShotRatingList()
        }
    }

    private fun setSingleShotRatingList(
        rating: List<SingleShotRatingItem> = _screenState.value.singleShotList
    ){
        _screenState.update {
            it.copy(
                singleShotList = sortSingleShotList(rating)
            )
        }
    }

    private fun setMultipleShotRatingList(
        rating: List<MultipleShotRatingItem> = _screenState.value.multipleShotList
    ){
        _screenState.update {
            it.copy(
                multipleShotList = sortMultipleShotList(rating)
            )
        }
    }

    fun deleteSingleShotRating(id: Int){
        viewModelScope.launch {
            repository.deleteSingleShotRatingItem(id)
        }
    }

    fun editSingleShotRating(singleShotRatingItem: SingleShotRatingItem){
        viewModelScope.launch {
            repository.updateSingleShotRatingItem(singleShotRatingItem)
        }
    }

    init {
        handle["isSingleShot"] = 0
        setIsSingleShot(handle.get<Int>("isSingleShot") == 1)

        /*viewModelScope.launch {
            repository.insertMultipleShotRatingItem(
                multipleShotRatingItem = MultipleShotRatingItem(
                    device = null,
                    bullet = null,
                    averageSpeed = 119.0,
                    averageEnergy = 3.84,
                    shots = listOf(
                        ShotRatingItem(
                            speed = 119.0,
                            energy = 3.84
                        ),
                        ShotRatingItem(
                            speed = 118.0,
                            energy = 3.80
                        ),
                        ShotRatingItem(
                            speed = 120.0,
                            energy = 3.88
                        ),
                        ShotRatingItem(
                            speed = 116.0,
                            energy = 3.74
                        ),
                        ShotRatingItem(
                            speed = 121.0,
                            energy = 3.94
                        ),
                        ShotRatingItem(
                            speed = 115.0,
                            energy = 3.64
                        ),
                        ShotRatingItem(
                            speed = 122.0,
                            energy = 4.04
                        ),
                    )
                ),
                deviceID = 14,
                bulletID = 3
            )
            repository.insertMultipleShotRatingItem(
                multipleShotRatingItem = MultipleShotRatingItem(
                    device = null,
                    bullet = null,
                    averageSpeed = 115.0,
                    averageEnergy = 3.24,
                    shots = listOf(
                        ShotRatingItem(
                            speed = 115.0,
                            energy = 3.24
                        ),
                        ShotRatingItem(
                            speed = 116.0,
                            energy = 3.84
                        ),
                        ShotRatingItem(
                            speed = 114.0,
                            energy = 3.80
                        ),
                        ShotRatingItem(
                            speed = 117.0,
                            energy = 3.88
                        ),
                        ShotRatingItem(
                            speed = 113.0,
                            energy = 3.74
                        ),
                        ShotRatingItem(
                            speed = 118.0,
                            energy = 3.94
                        ),
                        ShotRatingItem(
                            speed = 112.0,
                            energy = 3.64
                        )
                    )
                ),
                deviceID = 15,
                bulletID = 4
            )
        }*/

        viewModelScope.launch {
            if(handle.get<Int>("isSingleShot") == 1){
                repository.getSingleShotRating().collect{ rating ->
                    setSingleShotRatingList(rating)
                }
            }
            else {
                repository.getMultipleShotRating().collect{ rating ->
                    setMultipleShotRatingList(rating)
                }
            }
        }
    }
}