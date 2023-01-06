package com.example.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.DataState
import com.example.core.domain.UiComponent
import com.example.core.util.Logger
import com.example.hero_interactors.FilterHeroes
import com.example.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeroesInteractor: GetHeros,
    private val filterHeroesInteractor: FilterHeroes,
    @Named("heroListLogger")
    private val logger: Logger,
) : ViewModel() {

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())
    val searchQuery = MutableStateFlow("")

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
        filterHeroesInteractor(
            current = state.value.heros,
            heroName = searchQuery,
            heroFilter = state.value.heroFilter,
            attribute = state.value.primaryAttribute
        ).onEach {
            state.value = state.value.copy(filteredHeroList = it)
        }.launchIn(viewModelScope)

    }

    private fun onHeroNameChange(heroName: String) {
        state.value = state.value.copy(heroName = heroName)
        searchQuery.value = heroName

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