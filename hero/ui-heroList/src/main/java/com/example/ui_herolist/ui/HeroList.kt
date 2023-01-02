package com.example.ui_herolist

import HeroListItem
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.core.ProgressBarState
import com.example.hero_domain.Hero
import com.example.ui_herolist.ui.HeroListState

@ExperimentalAnimationApi
@Composable
fun HeroList(
    state: HeroListState,
    imageLoader: ImageLoader
) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(state.heros) { hero: Hero ->
                HeroListItem(
                    hero = hero,
                    imageLoader = imageLoader,
                    onSelectHero = {}
                )
            }
        }
        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}

