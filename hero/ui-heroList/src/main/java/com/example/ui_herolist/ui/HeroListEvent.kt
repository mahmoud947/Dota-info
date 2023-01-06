package com.example.ui_herolist.ui


sealed class HeroListEvent{
    object GetHeros:HeroListEvent()
    object FilterHeroes:HeroListEvent()
    data class OnHeroNameChange(val heroName:String):HeroListEvent()
}
