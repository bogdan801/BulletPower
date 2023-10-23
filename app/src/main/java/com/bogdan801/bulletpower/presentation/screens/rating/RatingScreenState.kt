package com.bogdan801.bulletpower.presentation.screens.rating

import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import com.bogdan801.bulletpower.presentation.components.SortBy
import com.bogdan801.bulletpower.presentation.components.SortOrder

data class RatingScreenState(
    val isSingleShot: Boolean = true,
    val sortBy: SortBy = SortBy.Energy,
    val sortOrder: SortOrder = SortOrder.Descending,
    val searchQuery: String = "",
    val singleShotList: List<SingleShotRatingItem> = listOf(),
    val multipleShotList: List<MultipleShotRatingItem> = listOf(),
    val foundSingleShots: List<SingleShotRatingItem> = listOf(),
    val foundMultipleShots: List<MultipleShotRatingItem> = listOf(),
    val expandedMutableShotCards: Map<Int, Boolean> = mapOf()
)