package com.lucasaugustocastro.animejotter.data.interfaces

import com.lucasaugustocastro.animejotter.models.SearchAnimeResultDTO


interface SearchReceiverDelegate {

    fun loadStatus(status:Boolean, searchAnimeResult: SearchAnimeResultDTO?)

}