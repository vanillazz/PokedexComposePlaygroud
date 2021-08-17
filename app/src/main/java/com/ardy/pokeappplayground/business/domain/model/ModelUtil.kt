package com.ardy.pokeappplayground.business.domain.model

fun urlToId(url: String): Int {
    return "/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isDigit() || it == '-' }.toInt()
}

fun urlToCat(url: String): String {
    return "/[a-z\\-]+/-?[0-9]+/$".toRegex().find(url)!!.value.filter { it.isLetter() || it == '-' }
}

fun resourceUrl(id: Int, category: String): String {
    return "/api/v2/$category/$id/"
}



//data class NamedApiResource(
//    val resId: Int = 0,
//    val name: String = "",
//    val url: String = ""
//) {
//    constructor(dto: ApiValueDto?) : this(
//        resId = dto?.url?.let { urlToId(it) } ?: 0,
//        name = dto?.name.orEmpty(),
//        url = dto?.url.orEmpty()
//    )
//}
