package com.ardy.pokeappplayground.presentation.ui.pokelist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.ardy.pokeappplayground.presentation.theme.AppTheme

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

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value
    ) {
        
        Column {
            Text(text = "Test Compose")
        }

    }

}