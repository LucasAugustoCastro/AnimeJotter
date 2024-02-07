package com.lucasaugustocastro.animejotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lucasaugustocastro.animejotter.data.ApiClient
import com.lucasaugustocastro.animejotter.data.interfaces.LoadReceiverDelegate
import com.lucasaugustocastro.animejotter.databinding.ActivityTitleDetailsBinding
import com.lucasaugustocastro.animejotter.models.AnimeDTO
import com.lucasaugustocastro.animejotter.models.AnimeResultDTO
import com.squareup.picasso.Picasso

class TitleDetailsActivity : AppCompatActivity(), LoadReceiverDelegate {
    private lateinit var anime: AnimeResultDTO
    private lateinit var binding: ActivityTitleDetailsBinding
    private val titleName by lazy { binding.txtTitleName }
    private val imgTitle by lazy { binding.imgTitle }
    private val releaseDate by lazy { binding.txtReleaseDate }
    private val author by lazy { binding.txtAuthor }
    private val titleDescription by lazy { binding.txtDescription }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var titleId: Long = -1
        intent.run {
            titleId = this.getLongExtra("title_id", -1)
            if(titleId.toInt() != -1) {
                ApiClient.getAnimeById(titleId, this@TitleDetailsActivity)

            }
        }
    }

    override fun loadStatus(status: Boolean, animeResult: AnimeResultDTO?) {
        if (status) {
            if (animeResult != null) {
                animeResult.data?.let { setTitleInfo(it) }
            }
        }else {
            Toast.makeText(this, "Um erro ocorreu", Toast.LENGTH_LONG)
        }
    }

    private fun setTitleInfo(data:AnimeDTO){

        titleName.text = data.title ?: ""
        Picasso.get().load(data.images?.jpg?.imageUrl).into(imgTitle)
        releaseDate.text = data.year.toString()
        author.text = data.studios?.get(0)?.name ?: "Não informado"
        titleDescription.text = data.synopsis
    }
}