package com.example.apiadventurewithrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiadventurewithrecyclerview.Pokemon

class PokemonAdapter(private val pokemonList: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // Bind data to the ViewHolder
        holder.tvName.text = "Name: ${pokemon.name}"
        holder.tvType.text = "Type: ${pokemon.type}"
        holder.tvBaseExperience.text = "Base Experience: ${pokemon.baseExperience}"
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvPokemonName)
        val tvType: TextView = itemView.findViewById(R.id.tvPokemonType)
        val tvBaseExperience: TextView = itemView.findViewById(R.id.tvBaseExperience)
    }
}
