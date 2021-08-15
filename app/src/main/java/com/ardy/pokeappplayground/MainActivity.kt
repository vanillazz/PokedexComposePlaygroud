package com.ardy.pokeappplayground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ccom.ardy.pokeappplayground.cache.datastore.SettingsDataStore
import com.ardy.pokeappplayground.presentation.navigation.Screen
import com.ardy.pokeappplayground.presentation.ui.pokelist.PokeListScreen
import com.ardy.pokeappplayground.presentation.ui.pokelist.PokeListViewModel
import com.ardy.pokeappplayground.util.connectionutil.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.PokeList.route) {

                /*  pokemon list page */
                composable(route = Screen.PokeList.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: PokeListViewModel = viewModel(this@MainActivity, "PokeListViewModel", factory)
                    PokeListScreen(
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onToggleTheme = settingsDataStore::toggleTheme,
                        onNavigateToPokeDetailScreen = navController::navigate,
                        viewModel = viewModel,
                    )
                }

                /*  pokemon detail page */
//                composable(
//                    route = Screen.PokeDetail.route + "/{pokeId}",
//                    arguments = listOf(navArgument("pokeId") {
//                        type = NavType.IntType
//                    })
//                ) { navBackStackEntry ->
//                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
//                    val viewModel: RecipeViewModel = viewModel(this@MainActivity, "RecipeDetailViewModel", factory)
//                    RecipeDetailScreen(
//                        isDarkTheme = settingsDataStore.isDark.value,
//                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
//                        recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
//                        viewModel = viewModel,
//                    )
//                }
            }
        }
    }
}