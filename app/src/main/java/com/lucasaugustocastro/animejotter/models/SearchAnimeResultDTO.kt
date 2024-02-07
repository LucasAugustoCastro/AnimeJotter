package com.lucasaugustocastro.animejotter.models

data class SearchAnimeResultDTO(
    val pages: PagesDTO?,
    val data: List<AnimeDTO>?
)
