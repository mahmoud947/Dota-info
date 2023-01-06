package com.example.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.UiComponent
import com.example.hero_domain.Hero
import com.example.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeroesInteractor: GetHeros,
    @Named("heroListLogger")
    private val logger: Logger,
) : ViewModel() {

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())


    init {
        onEvent(event = HeroListEvent.GetHeros)
    }


    fun onEvent(event: HeroListEvent) {
        when (event) {
            is HeroListEvent.GetHeros -> getHeros()
            is HeroListEvent.OnHeroNameChange -> onHeroNameChange(event.heroName)
            is HeroListEvent.FilterHeroes -> filterHeroes()
        }
    }

    private fun filterHeroes() {
       val filteredHeroes:MutableList<Hero> = state.value.heros.filter { hero ->
           hero.localizedName.trim().lowercase().contains(state.value.heroName.lowercase())
       }.toMutableList()
        state.value = state.value.copy(filteredHeroList = filteredHeroes)
    }

    private fun onHeroNameChange(heroName: String) {
        state.value= state.value.copy(heroName = heroName)
    }


    private fun getHeros() {
        getHeroesInteractor().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UiComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UiComponent.Dialog).description)
                        }
                        is UiComponent.None -> {
                            logger.log((dataState.uiComponent as UiComponent.None).message)
                        }
                    }

                }
                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data ?: listOf())
                    filterHeroes()
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(
                        progressBarState = dataState.progressBarState
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}