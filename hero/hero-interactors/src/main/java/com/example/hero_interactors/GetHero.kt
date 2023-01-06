package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UiComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetHero(
    private val cache: HeroCache
) {
    operator fun invoke(heroId: Int): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Loading))
            val hero: Hero = cache.getHero(id = heroId)
            if (hero == null) {
                throw Exception("hero not found")
            }
            emit(DataState.Data<Hero>(hero))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<Hero>(
                    uiComponent = UiComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading<Hero>(progressBarState = ProgressBarState.Idle))
        }
    }
}