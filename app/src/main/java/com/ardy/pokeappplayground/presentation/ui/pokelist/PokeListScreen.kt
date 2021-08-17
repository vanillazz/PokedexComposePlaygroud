package com.ardy.pokeappplayground.presentation.ui.pokelist

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ardy.pokeappplayground.business.domain.model.Poke
import com.ardy.pokeappplayground.presentation.navigation.Screen
import com.ardy.pokeappplayground.presentation.theme.AppTheme
import com.ardy.pokeappplayground.util.Constants.Companion.PAGINATION_PAGE_SIZE
import com.ardy.pokeappplayground.util.Constants.Companion.TAG
import com.ardy.pokeappplayground.util.DEFAULT_RECIPE_IMAGE
import com.ardy.pokeappplayground.util.capitalizeWords
import com.ardy.pokeappplayground.util.loadPicture

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun PokeListScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToPokeDetailScreen: (String) -> Unit,
    viewModel: PokeListViewModel,
) {

    val loading = viewModel.loading.value

    val scaffoldState = rememberScaffoldState()

    val dialogQueue = viewModel.dialogQueue

    val page = viewModel.page.value

    val pokemons = viewModel.pokemons.value

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value
    ) {
        Scaffold(
            topBar = {
                CustomAppBar()
            }
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                itemsIndexed(items = pokemons) { index, pokemon ->
                    viewModel.onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGINATION_PAGE_SIZE) && !loading) {
                        viewModel.onTriggerEvent(PokeListEvents.GetNextPageEvent)
                    }
                    PokeCard(pokemon, onClick = {
                        Log.d(TAG, "PokeListScreen: ${pokemon.name}")

                        val route = Screen.PokeDetail.route + "/${pokemon.id}"
                        onNavigateToPokeDetailScreen(route)
                    })
                }
            }
        }
    }
}

@Composable
fun CustomAppBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp,
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    text = "PokeDex Compose",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun PokeCard(
    poke: Poke,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {
            val image = loadPicture(
                url = poke.imgUrl,
                defaultImage = DEFAULT_RECIPE_IMAGE
            ).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "poke image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = poke.name.capitalizeWords(),
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                )
            }
        }
    }
}