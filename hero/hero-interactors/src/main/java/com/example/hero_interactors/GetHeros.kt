package com.example.hero_interactors

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UiComponent
import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.network.HeroService
import com.example.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    private val service: HeroService,
   private val cache: HeroCache
) {
    operator fun invoke(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Loading))
            val heros: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Hero>>(
                        uiComponent = UiComponent.Dialog(
                            title = "Network Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )
                listOf()
            }
            cache.insert(heros)
            val cachedHeros = cache.selectAll()
            emit(DataState.Data(cachedHeros))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<Hero>>(
                    uiComponent = UiComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading<List<Hero>>(progressBarState = ProgressBarState.Idle))
        }
    }
}