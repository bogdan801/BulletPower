package com.bogdan801.bulletpower.presentation.screens.graph

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
//import com.bogdan801.bulletpower.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GraphViewModel
@Inject
constructor(
    //private val repository: Repository
): ViewModel()  {
    private val _screenState = MutableStateFlow(GraphScreenState())
    val screenState = _screenState.asStateFlow()

    fun convertDataToPoints(data: List<ShotRatingItem>){
        _screenState.update {
            it.copy(
                speedPoints = data.mapIndexed { index, item ->
                    Point(
                        x = (index + 1).toFloat(),
                        y = item.speed.toFloat()
                    )
                },
                energyPoints = data.mapIndexed { index, item ->
                    Point(
                        x = (index + 1).toFloat(),
                        y = item.energy.toFloat()
                    )
                }
            )
        }
    }

    fun setDataToShowType(type: GraphDataType){
        _screenState.update {
            it.copy(
                dataToShow = type
            )
        }
    }
}