package com.ardy.pokeappplayground

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import ccom.ardy.pokeappplayground.cache.datastore.SettingsDataStore
import com.ardy.pokeappplayground.presentation.navigation.Screen
import com.ardy.pokeappplayground.presentation.ui.pokedetail.PokeDetailScreen
import com.ardy.pokeappplayground.presentation.ui.pokedetail.PokeDetailViewModel
import com.ardy.pokeappplayground.presentation.ui.pokelist.PokeListScreen
import com.ardy.pokeappplayground.presentation.ui.pokelist.PokeListViewModel
import com.ardy.pokeappplayground.util.Constants
import com.ardy.pokeappplayground.util.connectionutil.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(
                Constants.TAG,
                "onCreate: IS INTERNET AVAILABLE? ${connectivityManager.isNetworkAvailable.value}"
            )

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.PokeList.route) {

                /*  pokemon list page */
                composable(route = Screen.PokeList.route) { navBackStackEntry ->
                    PokeListScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onToggleTheme = settingsDataStore::toggleTheme,
                        onNavigateToPokeDetailScreen = navController::navigate,
                        viewModel = hiltViewModel<PokeListViewModel>(),
                    )
                }

                /*  pokemon detail page */
                composable(
                    route = Screen.PokeDetail.route + "/{pokemonId}",
                    arguments = listOf(navArgument("pokemonId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    PokeDetailScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        pokemonId = navBackStackEntry.arguments?.getInt("pokemonId"),
                        viewModel = hiltViewModel<PokeDetailViewModel>(),
                    )
                }
            }
        }
    }
}