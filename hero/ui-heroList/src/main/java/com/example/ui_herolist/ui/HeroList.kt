package com.example.ui_herolist

import HeroListItem
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.core.ProgressBarState
import com.example.hero_domain.Hero
import com.example.ui_herolist.components.HeroListToolbar
import com.example.ui_herolist.ui.HeroListEvent
import com.example.ui_herolist.ui.HeroListState


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader,
    event: (HeroListEvent) -> Unit,
    navigateToDetailScreen: (Int) -> Unit,
) {


    Box(modifier = Modifier.fillMaxSize()) {
        Column {


            HeroListToolbar(heroName = state.heroName, onHeroNameChanged = {
                event(HeroListEvent.OnHeroNameChange(heroName = it))
            }, onExecuteSearch = {
                event(HeroListEvent.FilterHeroes)
            }, onShowFilterDialog = {})
            LazyColumn {
                items(state.filteredHeroList) { hero: Hero ->
                    HeroListItem(hero = hero, imageLoader = imageLoader, onSelectHero = { heroId ->
                        navigateToDetailScreen(heroId)
                    })
                }
            }

        }

        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}

