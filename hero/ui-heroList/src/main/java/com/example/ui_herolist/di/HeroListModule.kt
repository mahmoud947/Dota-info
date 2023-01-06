package com.example.ui_herolist.di

import com.example.core.util.Logger
import com.example.hero_interactors.FilterHeroes
import com.example.hero_interactors.GetHeros
import com.example.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {

    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger(): Logger = Logger(tag = "HeroList")

    @Provides
    @Singleton
    fun provideGetHeros(
        herosInteractors: HeroInteractors
    ):GetHeros=herosInteractors.getHeros

    @Provides
    @Singleton
    fun provideFilterHeroes(
        herosInteractors: HeroInteractors
    ):FilterHeroes=herosInteractors.filterHeroes
}