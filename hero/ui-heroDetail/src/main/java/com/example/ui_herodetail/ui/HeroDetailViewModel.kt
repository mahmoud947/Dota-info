package com.example.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.DataState
import com.example.hero_interactors.GetHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val getHero: GetHero,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())
    val state: State<HeroDetailState> get() = _state

    init {
        savedStateHandle.get<Int>("heroId")?.let {
            onEvent(HeroDetailEvent.GetHero(heroId = it))
        }
    }

    fun onEvent(event: HeroDetailEvent) {
        when (event) {
            is HeroDetailEvent.GetHero -> handelGetHero(heroId = event.heroId)
        }
    }

    private fun handelGetHero(heroId: Int) {
        getHero(heroId = heroId).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    _state.value = _state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    //TODO(Handle Error)
                }
                is DataState.Data -> {
                    _state.value = _state.value.copy(hero = dataState.data)
                }
            }

        }.launchIn(viewModelScope)
    }


}