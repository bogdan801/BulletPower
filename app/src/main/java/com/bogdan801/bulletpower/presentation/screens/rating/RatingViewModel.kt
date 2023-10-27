package com.bogdan801.bulletpower.presentation.screens.rating

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import com.bogdan801.bulletpower.domain.repository.Repository
import com.bogdan801.bulletpower.presentation.components.SortBy
import com.bogdan801.bulletpower.presentation.components.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    //type of screen content
    private fun setIsSingleShot(isSingleShot: Boolean){
        _screenState.update {
            it.copy(
                isSingleShot = isSingleShot
            )
        }
    }

    //search
    fun doSearch(searchQuery: String = _screenState.value.searchQuery) {
        _screenState.update {
            it.copy(
                searchQuery = searchQuery
            )
        }

        viewModelScope.launch {
            if(_screenState.value.isSingleShot){
                _screenState.update {
                    it.copy(
                        foundSingleShots = sortSingleShotList(
                            repository.searchSingleShotRating(
                                searchQuery = searchQuery,
                                listToSearch = _screenState.value.singleShotList
                            )
                        )
                    )
                }
            }
            else {
                _screenState.update {
                    it.copy(
                        foundMultipleShots = sortMultipleShotList(
                            repository.searchMultipleShotRating(
                                searchQuery = searchQuery,
                                listToSearch = _screenState.value.multipleShotList
                            )
                        )
                    )
                }
            }
        }

    }

    //sorting
    private fun sortSingleShotList(list: List<SingleShotRatingItem>) = when(_screenState.value.sortOrder){
        SortOrder.Ascending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedBy { it.device?.name }
                SortBy.DeviceType -> list.sortedBy { it.device?.type }
                SortBy.DeviceCaliber -> list.sortedBy { it.device?.caliber }
                SortBy.BulletName -> list.sortedBy { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedBy { it.bullet?.weight }
                SortBy.BulletCaliber -> list.sortedBy { it.bullet?.caliber }
                SortBy.Speed -> list.sortedBy { it.speed }
                SortBy.Energy -> list.sortedBy { it.energy }
            }
        }
        SortOrder.Descending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedByDescending { it.device?.name }
                SortBy.DeviceType -> list.sortedByDescending { it.device?.type }
                SortBy.DeviceCaliber -> list.sortedByDescending { it.device?.caliber }
                SortBy.BulletName -> list.sortedByDescending { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedByDescending { it.bullet?.weight }
                SortBy.BulletCaliber -> list.sortedByDescending { it.bullet?.caliber }
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
                SortBy.DeviceCaliber -> list.sortedBy { it.device?.caliber }
                SortBy.BulletName -> list.sortedBy { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedBy { it.bullet?.weight }
                SortBy.BulletCaliber -> list.sortedBy { it.bullet?.caliber }
                SortBy.Speed -> list.sortedBy { it.averageSpeed }
                SortBy.Energy -> list.sortedBy { it.averageEnergy }
            }
        }
        SortOrder.Descending -> {
            when(_screenState.value.sortBy){
                SortBy.DeviceName -> list.sortedByDescending { it.device?.name }
                SortBy.DeviceType -> list.sortedByDescending { it.device?.type }
                SortBy.DeviceCaliber -> list.sortedByDescending { it.device?.caliber }
                SortBy.BulletName -> list.sortedByDescending { it.bullet?.name }
                SortBy.BulletWeight -> list.sortedByDescending { it.bullet?.weight }
                SortBy.BulletCaliber -> list.sortedByDescending { it.bullet?.caliber }
                SortBy.Speed -> list.sortedByDescending { it.averageSpeed }
                SortBy.Energy -> list.sortedByDescending { it.averageEnergy }
            }
        }
    }

    fun setSortOrder(order: SortOrder){
        _screenState.update {
            it.copy(
                sortOrder = order
            )
        }
        if(_screenState.value.isSingleShot) setSingleShotRatingList()
        else setMultipleShotRatingList()

        if(_screenState.value.searchQuery.isNotBlank()) doSearch()
    }

    fun setSortBy(sortBy: SortBy){
        _screenState.update {
            it.copy(
                sortBy = sortBy
            )
        }
        if(_screenState.value.isSingleShot) setSingleShotRatingList()
        else setMultipleShotRatingList()
    }

    //data operations
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

    fun deleteSingleShotRating(id: Int, doSearch: Boolean = false){
        viewModelScope.launch {
            repository.deleteSingleShotRatingItem(id)
            if(doSearch) {
                delay(100)
                doSearch()
            }
        }
    }

    fun editSingleShotRating(singleShotRatingItem: SingleShotRatingItem, doSearch: Boolean = false){
        viewModelScope.launch {
            repository.updateSingleShotRatingItem(
                singleShotRatingItem,
                singleShotRatingItem.device!!.deviceID,
                singleShotRatingItem.bullet!!.bulletID
            )
            if(doSearch) {
                delay(100)
                doSearch()
            }
        }
    }

    fun deleteMultipleShotRating(id: Int, doSearch: Boolean = false){
        viewModelScope.launch {
            repository.deleteMultipleShotRatingItem(id)
            if(doSearch) {
                delay(100)
                doSearch()
            }
        }
    }

    fun deleteShotFromMultipleShotRating(shotID: Int, doSearch: Boolean = false){
        viewModelScope.launch {
            repository.deleteShotRatingItem(shotID)
            if(doSearch) {
                delay(100)
                doSearch()
            }
        }
    }

    fun editShotFromMultipleShotRating(editedShot: ShotRatingItem, doSearch: Boolean = false){
        viewModelScope.launch {
            repository.insertShotRatingItem(editedShot)
            if(doSearch) {
                delay(100)
                doSearch()
            }
        }
    }

    //ui related
    fun toggleMultipleShotCard(multipleShotID: Int){
        val isExpanded = _screenState.value.expandedMutableShotCards[multipleShotID] ?: false
        val isAnyExpanded = _screenState.value.expandedMutableShotCards.values.contains(true)
        collapseAllCards()
        if(!isExpanded) {
            viewModelScope.launch {
                if(isAnyExpanded) delay(200)
                expandCard(multipleShotID)
            }
        }
    }

    private fun expandCard(id: Int) {
        _screenState.update {
            it.copy(
                expandedMutableShotCards = _screenState.value.expandedMutableShotCards
                    .toMutableMap()
                    .apply {
                        set(id, true)
                    }
            )
        }
    }

    private fun collapseAllCards() {
        _screenState.update {
            it.copy(
                expandedMutableShotCards = mapOf()
            )
        }
    }

    init {
        setIsSingleShot(handle.get<Int>("isSingleShot") == 1)

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