package com.bogdan801.bulletpower.presentation.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private fun calculateBulletEnergy(speed: Double, mass: Double) = ((mass * (speed * speed)) / 2) * 0.001
}