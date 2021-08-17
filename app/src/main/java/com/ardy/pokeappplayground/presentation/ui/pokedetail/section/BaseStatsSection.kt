package com.ardy.pokeappplayground.presentation.ui.pokedetail.section

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ardy.pokeappplayground.business.domain.model.PokeDetail
import com.ardy.pokeappplayground.util.capitalizeWords

data class Stat(
    val label: String,
    val value: Int?,
    val max: Int = 100
) {
    val progress: Float =
        1f * (value ?: 0) / max
}

val cell1weight = .20f
val cell2weight = .15f
val cell3weight = .65f

@Composable
fun BaseStatsSection(pokemon: PokeDetail) {

    var stats = mutableListOf<Stat>()
    pokemon.stats.forEach {
        var name = it.stat?.replace("Special-", "Sp.")?.capitalizeWords().orEmpty()
        stats.add(Stat(name, it.baseStat))
    }
    StatsTable(stats)
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(4.dp)
    )
}

@Composable
private fun StatsTable(stats: List<Stat>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        stats.forEachIndexed { index, stat ->
            Row {
                TableCell(text = stat.label, weight = cell1weight)
                TableCell(text = stat.value.toString(), weight = cell2weight)
                Box(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 8.dp)
                    .weight(cell3weight)
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier.height(10.dp),
                        progress = stat.progress
                    )
                }
            }
        }
    }
}