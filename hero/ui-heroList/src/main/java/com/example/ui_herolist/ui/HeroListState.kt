package com.example.ui_herolist.ui

import com.example.core.domain.ProgressBarState
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros:List<Hero> = emptyList(),
    val heroName:String = "",
    val filteredHeroList:List<Hero> = emptyList(),
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown
)
