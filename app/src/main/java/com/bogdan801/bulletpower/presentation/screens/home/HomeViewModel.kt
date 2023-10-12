package com.bogdan801.bulletpower.presentation.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: Repository,
    handle: SavedStateHandle
): ViewModel() {
    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    fun setDevice(device: Device?){
        _screenState.update {
            it.copy(
                device = device
            )
        }
    }

    fun setBullet(bullet: Bullet?){
        _screenState.update {
            it.copy(
                bullet = bullet
            )
        }
        if(bullet!=null) setBulletWeight(bullet.weight)
    }

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


    fun isSingleShotItemPresent(shot: SingleShotRatingItem) : Boolean {
        var result: Boolean
        runBlocking {
            result = repository.isSingleShotItemPresent(shot) != -1
        }
        return result
    }

    fun addSingleShotToRating(shot: SingleShotRatingItem){
        viewModelScope.launch {
            if(_screenState.value.device != null && _screenState.value.bullet != null){
                repository.insertSingleShotRatingItem(
                    singleShotRatingItem = shot,
                    deviceID = _screenState.value.device!!.deviceID,
                    bulletID = _screenState.value.bullet!!.bulletID
                )
            }
        }
    }

    fun addSeriesToRating(multipleShotRatingItem: MultipleShotRatingItem){
        viewModelScope.launch {
            if(_screenState.value.device != null && _screenState.value.bullet != null){
                repository.insertMultipleShotRatingItem(
                    multipleShotRatingItem = multipleShotRatingItem,
                    deviceID = _screenState.value.device!!.deviceID,
                    bulletID = _screenState.value.bullet!!.bulletID
                )
            }
        }
    }

    private fun calculateBulletEnergy(speed: Double, mass: Double) = ((mass * (speed * speed)) / 2) * 0.001
}