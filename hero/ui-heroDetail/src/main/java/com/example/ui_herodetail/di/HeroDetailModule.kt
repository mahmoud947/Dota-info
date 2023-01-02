package com.example.ui_herodetail.di

import com.example.hero_interactors.GetHero
import com.example.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {
    @Provides
    @Singleton
    fun provideGetHero(
        heroInteractors: HeroInteractors
    ):GetHero=heroInteractors.getHero
}