package com.example.ui_herolist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.UiComponent
import com.example.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeroesInteractor: GetHeros
) : ViewModel() {
    private val logger = Logger(tag = "GetHerosTest")
     val state: MutableState<HeroListState> = mutableStateOf(HeroListState())


    init {
        getHeros()
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