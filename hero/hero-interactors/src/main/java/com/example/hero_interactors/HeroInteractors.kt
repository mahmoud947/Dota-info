package com.example.hero_interactors

import com.example.hero_datasource.cache.HeroCache
import com.example.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeros: GetHeros,
    val getHero: GetHero
) {
    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeros = GetHeros(
                    service = HeroService.build(),
                    cache = cache
                ),
                getHero = GetHero(cache = cache)
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema

        val dbName: String = HeroCache.dbName
    }
}