package com.example.hero_interactors

import com.example.core.domain.FilterOrder
import com.example.hero_domain.Hero
import com.example.hero_domain.HeroAttribute
import com.example.hero_domain.HeroFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.round

class FilterHeroes {
    operator fun invoke(
        current: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        attribute: HeroAttribute
    ): Flow<List<Hero>> = flow {
        val filteredHeroes: MutableList<Hero> = current.filter { hero ->
            hero.localizedName.lowercase()
                .contains(heroName.trim().lowercase())
        }.toMutableList()
        when (heroFilter) {
            is HeroFilter.Hero -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filteredHeroes.sortByDescending { it.localizedName }
                    }
                    is FilterOrder.Ascending -> {
                        filteredHeroes.sortBy { it.localizedName }
                    }

                }
            }

            is HeroFilter.ProWins -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filteredHeroes.sortByDescending {
                            getWinRate(
                                proWins = it.proWins.toDouble(), proPick = it.proPick.toDouble()
                            )
                        }

                    }
                    is FilterOrder.Ascending -> {
                        filteredHeroes.sortBy {
                            getWinRate(
                                proWins = it.proWins.toDouble(), proPick = it.proPick.toDouble()
                            )
                        }

                    }

                }
            }
        }

        when (attribute) {
            is HeroAttribute.Strength -> {
                filteredHeroes.filter { it.primaryAttribute is HeroAttribute.Strength }

            }
            is HeroAttribute.Intelligence -> {
                filteredHeroes.filter { it.primaryAttribute is HeroAttribute.Intelligence }

            }
            is HeroAttribute.Agility -> {
                filteredHeroes.filter { it.primaryAttribute is HeroAttribute.Agility }

            }
            is HeroAttribute.Unknown -> {

            }
        }
        emit(filteredHeroes)
    }


    private fun getWinRate(proWins: Double, proPick: Double): Int {
        return if (proPick <= 0) {
            0
        } else {
            val winRate: Int = round(proWins / proPick * 100).toInt()
            winRate
        }
    }
}