package com.lucasaugustocastro.animejotter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.adapter.SearchAdapter
import com.lucasaugustocastro.animejotter.data.ApiClient
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.data.interfaces.SearchReceiverDelegate
import com.lucasaugustocastro.animejotter.databinding.ActivitySearchTitleBinding
import com.lucasaugustocastro.animejotter.models.AnimeDTO
import com.lucasaugustocastro.animejotter.models.SearchAnimeResultDTO

class SearchTitleActivity : AppCompatActivity(), SearchReceiverDelegate {
    private lateinit var binding: ActivitySearchTitleBinding
    private lateinit var adapter: SearchAdapter
    private var customerId: Long = -1
    private val recyclerView by lazy { binding.rcvSearch }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchTitleBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setCustomerId()
        configureSearchButton()

    }

    override fun loadStatus(status: Boolean, searchAnimeResult: SearchAnimeResultDTO?) {
        if (status) {
            if (searchAnimeResult != null) {
                searchAnimeResult.data?.let {  setAdapter(it) }
            }
        } else {
            Toast.makeText(this, "Um erro ocorreu", Toast.LENGTH_LONG).show()
        }
    }

    private fun setCustomerId() {
        val sharedPreferences =
            this.getSharedPreferences("com.lucasaugustocastro.animejotter.userToken", Context.MODE_PRIVATE)
        customerId = sharedPreferences.getLong("user_id", -1)
    }

    private fun setAdapter(searchedTitles:  List<AnimeDTO>) {
        adapter = SearchAdapter(searchedTitles.orEmpty())
        recyclerView.adapter = adapter

        adapter.setOnClickListener(object :
            SearchAdapter.OnClickListener{
                override fun onClick(position: Int, model: AnimeDTO) {
                    AlertDialog.Builder(this@SearchTitleActivity).run{
                        setMessage("Deseja incluir esse anime ao seus animes assistidos ?")
                        setPositiveButton("Incluir") {_,_ ->
                            val id = saveWatchedAnime(model)
                            if (id > -1) {
                                setResult(RESULT_OK)
                                finish()
                            } else {
                                showMessage("Erro ao cadastar titulo. Tente novamente mais tarde")
                            }
                        }
                        setNegativeButton("Cancelar", null)
                        show()
                    }
                }
            }
        )
    }
    private fun saveWatchedAnime(anime: AnimeDTO): Long {
        var studio = "Não informado"
        if (anime.studios?.isEmpty() == false){
            studio = anime.studios[0].name.toString()
        }

        val title = WatchedTitle(
            title_id=anime.malId!!.toLong(),
            title_image= anime.images?.jpg?.imageUrl ?: "",
            name=anime.title ?: "",
            author=studio,
            release_at=anime.year.toString(),
            description=anime.synopsis ?: "",
            favorite=false,
            customer_id=customerId,
        )
        return DataStore.saveWatchedTitle(title)
    }
    private fun configureSearchButton(){
        binding.btnSearch.setOnClickListener{
            val searched = binding.txtSearch.text.toString()
            ApiClient.getAnimeByName(searched, this)
        }
    }

    private fun showMessage(message: String){
        Snackbar.make(
            this,
            binding.layout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}