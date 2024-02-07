package com.lucasaugustocastro.animejotter.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.lucasaugustocastro.animejotter.data.interfaces.LoadReceiverDelegate
import com.lucasaugustocastro.animejotter.data.interfaces.SearchReceiverDelegate
import com.lucasaugustocastro.animejotter.models.AnimeResultDTO
import com.lucasaugustocastro.animejotter.models.SearchAnimeResultDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

object ApiClient{
    private const val BASE_URL = "https://api.jikan.moe/v4/anime"
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun getAnimeByName(name:String, delegate: SearchReceiverDelegate) {
        var uri = Uri.parse(BASE_URL)
            .buildUpon()
            .appendQueryParameter("q", name)

        executor.execute{
            val jsonStr = getRequestResponse(uri.toString(), "GET", null)
            try {
                val gson = Gson()
                var searchResult = gson.fromJson(jsonStr, SearchAnimeResultDTO::class.java)

                handler.post {
                    delegate?.loadStatus(true, searchResult)
                }

            } catch (e: Exception){
                e.printStackTrace()
                handler.post {
                    delegate?.loadStatus(false, null)
                }
            }
        }
    }

    fun getAnimeById(id:Long, delegate: LoadReceiverDelegate){
        val uri = Uri.parse(BASE_URL)
            .buildUpon()
            .appendPath(id.toString())

        executor.execute{
            val jsonStr = getRequestResponse(uri.toString(), "GET", null)
            try {
                val gson = Gson()
                var animeResult = gson.fromJson(jsonStr, AnimeResultDTO::class.java)

                handler.post {
                    delegate?.loadStatus(true, animeResult)
                }

            } catch (e: Exception){
                e.printStackTrace()
                handler.post {
                    delegate?.loadStatus(false, null)
                }
            }
        }
    }

    private fun getRequestResponse(endPoint: String, method: String, parameters: String?): String {
        val jsonStr = StringBuffer()
        val url = URL(endPoint)
        val connection = url.openConnection() as HttpURLConnection

        try {
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    var inputLine = reader.readLine()
                    while (inputLine != null) {
                        jsonStr.append(inputLine)
                        inputLine = reader.readLine()
                    }
                    reader.close()
                }
            } else {
                println("Erro: responseCode(${connection.responseCode})")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }

        return jsonStr.toString()
    }

}