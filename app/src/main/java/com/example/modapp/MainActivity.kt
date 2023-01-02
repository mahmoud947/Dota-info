package com.example.modapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.example.core.DataState
import com.example.core.Logger
import com.example.core.ProgressBarState
import com.example.core.UiComponent
import com.example.hero_interactors.HeroInteractors
import com.example.modapp.ui.theme.ModAppTheme
import com.example.ui_herolist.HeroList
import com.example.ui_herolist.ui.HeroListState
import com.example.ui_herolist.ui.HeroListViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private lateinit var imageLoader:ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        imageLoader=ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(.25)
            .crossfade(true)
            .build()

        setContent {
            ModAppTheme {
                val viewModel:HeroListViewModel = hiltViewModel()
                HeroList(
                    state = viewModel.state.value,
                    imageLoader=imageLoader
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", style=TextStyle(color = Color.Cyan), modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ModAppTheme {
        Greeting("Android")
    }
}