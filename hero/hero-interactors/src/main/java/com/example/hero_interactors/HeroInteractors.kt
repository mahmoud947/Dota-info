package com.example.hero_interactors

import com.example.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeros: GetHeros
){
    companion object Factory{
        fun build():HeroInteractors{
            return HeroInteractors(
                getHeros = GetHeros(
                    service = HeroService.build()
                )
            )
        }
    }
}