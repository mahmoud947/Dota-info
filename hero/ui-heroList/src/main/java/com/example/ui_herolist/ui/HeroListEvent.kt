package com.example.ui_herolist.ui

import kotlinx.coroutines.flow.Flow


sealed class HeroListEvent{
    object GetHeros:HeroListEvent()
    object FilterHeroes:HeroListEvent()
    data class OnHeroNameChange(val heroName: String):HeroListEvent()
}
