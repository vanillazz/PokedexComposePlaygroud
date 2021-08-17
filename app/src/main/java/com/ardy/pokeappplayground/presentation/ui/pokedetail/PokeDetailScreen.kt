package com.ardy.pokeappplayground.presentation.ui.pokedetail

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StampedPathEffectStyle.Companion.Rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ardy.pokeappplayground.R
import com.ardy.pokeappplayground.business.domain.model.PokeDetail
import com.ardy.pokeappplayground.business.domain.model.bgcolor
import com.ardy.pokeappplayground.presentation.component.common.PokemonTypeLabels
import com.ardy.pokeappplayground.presentation.component.common.TypeLabelMetrics.Companion.MEDIUM
import com.ardy.pokeappplayground.presentation.theme.AppTheme
import com.ardy.pokeappplayground.presentation.ui.pokedetail.section.BaseStatsSection
import com.ardy.pokeappplayground.presentation.ui.pokedetail.section.EvolutionSection
import com.ardy.pokeappplayground.util.Constants.Companion.TAG
import com.ardy.pokeappplayground.util.DEFAULT_RECIPE_IMAGE
import com.ardy.pokeappplayground.util.capitalizeWords
import com.ardy.pokeappplayground.util.loadPicture

@ExperimentalMaterialApi
@Composable
fun PokeDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    pokemonId: Int?,
    viewModel: PokeDetailViewModel
) {

    if (pokemonId == null) {

    } else {
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(PokeDetailEvents.GetPokemonDetailEvent(pokemonId))
        }

        val loading = viewModel.loading.value

        val scaffoldState = rememberScaffoldState()

        val dialogQueue = viewModel.dialogQueue

        val pokemonDetail = viewModel.pokemonDetail.value

        AppTheme(
            darkTheme = isDarkTheme,
            isNetworkAvailable = isNetworkAvailable,
            displayProgressBar = loading,
            scaffoldState = scaffoldState,
            dialogQueue = dialogQueue.queue.value
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
//                Text(
//                    text = "get pokemon with id $pokemonId"
//                )
//                pokemonDetail?.let {
//                    Text(
//                        text = pokemonDetail.name
//                    )
//                }
                pokemonDetail?.let {
                    Log.d(TAG, "PokeDetailScreen: ${it.name} - ${it.bgcolor()}")
                    Surface(color = it.bgcolor()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            PokeBallDecor()
                            HeaderLeft(pokemonDetail)
                            HeaderRight(pokemonDetail)
                            CardContent(pokemonDetail)
                            PokemonImage(pokemonDetail)
                        }
                    }
                }
            }


        }
    }
}

@Composable
private fun BoxScope.HeaderRight(pokemon: PokeDetail) {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 40.dp, end = 32.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = "#${pokemon.id.toString().padStart(3, '0')}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.align(Alignment.End),
                text = pokemon.eggGroups.first().capitalizeWords() ?: "",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
private fun BoxScope.HeaderLeft(pokemon: PokeDetail) {
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 40.dp, start = 32.dp)
    ) {
        Column {
            Text(
                text = pokemon.name.capitalizeWords(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White
                )
            )

            val listOfType = pokemon.types.map { it.type }
            Row {
                PokemonTypeLabels(listOfType, MEDIUM)
            }

        }
    }
}

private enum class Sections(val title: String) {
    BaseStats("Base stats"),
    Evolution("Evolution")
}

@Composable
private fun BoxScope.CardContent(pokemon: PokeDetail) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 270.dp)
    ) {
        Surface(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) {

            val sectionTitles = Sections.values().map { it.title }
            var state by remember { mutableStateOf(0) }
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(32.dp))
                TabRow(
                    selectedTabIndex = state,
                    backgroundColor = Color.White
                ) {
                    sectionTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = state == index,
                            onClick = { state = index }
                        )
                    }
                }

                Box(modifier = Modifier.padding(24.dp)) {
//                    BaseStatsSection(pokemon)
                    when (state) {
                        0 -> BaseStatsSection(pokemon)
                        1 -> EvolutionSection(pokemon)
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.PokemonImage(pokemon: PokeDetail) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(
                top = 130.dp
            )
            .size(200.dp)
    ) {
        val image = loadPicture(
            url = pokemon.imgUrl,
            defaultImage = DEFAULT_RECIPE_IMAGE
        ).value
        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = "poke image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
            )
        }

    }
}

@Composable
private fun BoxScope.PokeBallDecor() {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(
                top = 130.dp
            )
            .size(200.dp)
    ) {
        val image = loadPicture(
            drawableId = R.drawable.pokeball
        ).value
        image?.let { img ->
            Image(
                modifier = Modifier.alpha(0.3f).fillMaxWidth(),
                bitmap = img.asImageBitmap(),
                contentDescription = "poke image",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = Color(0xFFF5F5F5),
                    blendMode = BlendMode.SrcIn
                )
            )
        }

    }
}


