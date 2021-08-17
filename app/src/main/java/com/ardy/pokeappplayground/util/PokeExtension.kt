package com.ardy.pokeappplayground.util


fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
    it.lowercase().replaceFirstChar { char -> char.titlecase() }
}
