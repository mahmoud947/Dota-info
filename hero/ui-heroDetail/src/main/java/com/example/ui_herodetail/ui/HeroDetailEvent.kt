package com.example.ui_herodetail.ui

sealed class HeroDetailEvent {
    data class GetHero(
        val heroId: Int
    ) : HeroDetailEvent()
}
