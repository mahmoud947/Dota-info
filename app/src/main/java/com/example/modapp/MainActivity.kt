package com.example.modapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.ProgressBarState
import com.example.core.UiComponent
import com.example.hero_domain.Hero
import com.example.hero_interactors.HeroInteractors
import com.example.modapp.ui.theme.ModAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val heros: MutableState<List<Hero>> = mutableStateOf(listOf())
    private val progressBarState: MutableState<ProgressBarState> =
        mutableStateOf(ProgressBarState.Idle)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val getHeros = HeroInteractors.build().getHeros
        val logger = Logger(tag = "GetHerosTest")
        getHeros().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UiComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UiComponent.Dialog).description)
                        }
                        is UiComponent.None -> {
                            logger.log((dataState.uiComponent as UiComponent.None).message)
                        }
                    }

                }
                is DataState.Data -> {
                    heros.value = dataState.data ?: listOf()
                }
                is DataState.Loading -> {
                    progressBarState.value = dataState.progressBarState
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
        setContent {
            ModAppTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }

                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn {
                        items(heros.value) { hero: Hero ->
                            Text(text = hero.localizedName)
                        }
                    }
                    if (progressBarState.value is ProgressBarState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                }


            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ModAppTheme {
        Greeting("Android")
    }
}