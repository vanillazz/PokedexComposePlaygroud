package com.ardy.pokeappplayground.presentation.ui.pokedetail.section

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ardy.pokeappplayground.business.domain.model.PokeDetail

@Composable
fun EvolutionSection(pokemon : PokeDetail) {
    Column(modifier = Modifier.fillMaxWidth()) {
        pokemon.evoChain.forEachIndexed { index, evo ->
            Row {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "$index. $evo"
                )
            }
        }
    }
}