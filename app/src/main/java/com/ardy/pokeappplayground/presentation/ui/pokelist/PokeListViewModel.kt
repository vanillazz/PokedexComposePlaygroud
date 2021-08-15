package com.ardy.pokeappplayground.presentation.ui.pokelist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ardy.pokeappplayground.util.DialogQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel
@Inject constructor() : ViewModel() {


    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

}