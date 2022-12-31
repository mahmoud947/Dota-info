package com.example.hero_interactors

import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeros: GetHeros
){
    companion object Factory{
        fun build(sqlDriver: SqlDriver):HeroInteractors{
            return HeroInteractors(
                getHeros = GetHeros(
                    service = HeroService.build(),
                    cache = HeroCache.build(sqlDriver)
                )
            )
        }
    }
}