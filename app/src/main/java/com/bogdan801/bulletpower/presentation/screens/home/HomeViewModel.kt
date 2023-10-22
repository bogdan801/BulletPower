package com.bogdan801.bulletpower.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.bulletpower.data.util.calculateBulletEnergyUtil
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel() {
    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    fun setBulletWeight(bulletWeight: Double){
        _screenState.update {
            it.copy(
                bulletWeight = bulletWeight,
                singleShotEnergy = calculateBulletEnergy(
                    speed = _screenState.value.singleShotSpeed,
                    mass = bulletWeight
                ),
                multipleShotEnergy = calculateBulletEnergy(
                    speed = _screenState.value.multipleShotSpeed,
                    mass = bulletWeight
                )
            )
        }
    }

    fun setSingleShotSpeed(speed: Double){
        _screenState.update {
            it.copy(
                singleShotSpeed = speed,
                singleShotEnergy = calculateBulletEnergy(speed, _screenState.value.bulletWeight)
            )
        }
    }

    fun setMultipleShotSpeed(speed: Double){
        _screenState.update {
            it.copy(
                multipleShotSpeed = speed,
                multipleShotEnergy = calculateBulletEnergy(speed, _screenState.value.bulletWeight)
            )
        }
    }

    fun addShotToTheSeries(shot: ShotRatingItem){
        _screenState.update {
            it.copy(
                shotSeries = it.shotSeries.toMutableList().apply { add(shot) }
            )
        }
    }

    fun removeShotFromTheSeries(id: Int){
        _screenState.update {
            it.copy(
                shotSeries = it.shotSeries.toMutableList().apply { removeAt(id) }
            )
        }
    }

    fun clearSeries(){
        _screenState.update {
            it.copy(
                shotSeries = listOf()
            )
        }
    }


    fun isSingleShotItemPresent(shot: SingleShotRatingItem) : Boolean {
        var result: Boolean
        runBlocking {
            result = repository.isSingleShotItemPresent(shot) != -1
        }
        return result
    }

    fun addSingleShotToRating(shot: SingleShotRatingItem, device: Device, bullet: Bullet){
        viewModelScope.launch {
            repository.insertSingleShotRatingItem(
                singleShotRatingItem = shot,
                deviceID = device.deviceID,
                bulletID = bullet.bulletID
            )
        }
    }

    fun addSeriesToRating(multipleShotRatingItem: MultipleShotRatingItem, device: Device, bullet: Bullet){
        viewModelScope.launch {
            repository.insertMultipleShotRatingItem(
                multipleShotRatingItem = multipleShotRatingItem,
                deviceID = device.deviceID,
                bulletID = bullet.bulletID
            )
        }
    }

    private fun calculateBulletEnergy(speed: Double, mass: Double) = calculateBulletEnergyUtil(speed, mass)
}