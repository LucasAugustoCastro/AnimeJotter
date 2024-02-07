package com.lucasaugustocastro.animejotter.data.interfaces

import com.lucasaugustocastro.animejotter.models.AnimeResultDTO


interface LoadReceiverDelegate {
    fun loadStatus(status:Boolean, animeResult: AnimeResultDTO?)
}
