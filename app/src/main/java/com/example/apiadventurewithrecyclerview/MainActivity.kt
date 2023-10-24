package com.example.apiadventurewithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private var pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Existing code...
        recyclerView = findViewById(R.id.rvPokemonList)
        adapter = PokemonAdapter(pokemonList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val button = findViewById<Button>(R.id.btnGetPokemon)

        button.setOnClickListener {
            fetchRandomPokemon()
        }
    }

    private fun fetchRandomPokemon() {
        val client = AsyncHttpClient()
        val apiUrl = "https://pokeapi.co/api/v2/pokemon/${getRandomPokemonId()}"

        client[apiUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val pokemon = json.jsonObject
                    val name = pokemon.getString("name")
                    val type = pokemon.getJSONArray("types")
                        .getJSONObject(0)
                        .getJSONObject("type")
                        .getString("name")
                    val baseExperience = if (pokemon.has("base_experience")) {
                        pokemon.getInt("base_experience")
                    } else {
                        0
                    }

                    // Add the retrieved Pokemon to the list
                    pokemonList.add(Pokemon(name, type, baseExperience))

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable) {
                if (headers != null) {
                    Log.d("API Call", "Failed: $response")
                } else {
                    Log.d("API Call", "Failed with no headers. Status code: $statusCode")
                }

                throwable.printStackTrace()
            }

        }]
    }

    // Existing code...

    private fun getRandomPokemonId(): Int {
        // Generate a random Pokemon ID (assuming there are at least 1000 Pokemon)
        return (Math.random() * 1000).toInt() + 1
    }

}