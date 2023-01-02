package com.example.ui_herodetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.ui_herodetail.ui.HeroDetailState

@Composable
fun HeroDetail(
    heroDetailState: HeroDetailState
) {
   heroDetailState.hero?.let { 
       Text(text = it.localizedName)
   }?: Text(text = "LOADING.....")
}