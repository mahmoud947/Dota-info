package com.example.ui_herolist.ui

import com.example.hero_domain.HeroFilter
import kotlinx.coroutines.flow.Flow


sealed class HeroListEvent{
    object GetHeros:HeroListEvent()
    object FilterHeroes:HeroListEvent()
    data class OnHeroNameChange(val heroName: String):HeroListEvent()
    data class OnUpdateHeroFilter(val heroFilter: HeroFilter):HeroListEvent()
}
