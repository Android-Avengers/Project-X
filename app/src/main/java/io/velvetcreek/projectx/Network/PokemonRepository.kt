package io.velvetcreek.projectx.Network

import io.velvetcreek.projectx.Model.PokemonApiResponse
import retrofit2.Response

class PokemonRepository {

    private lateinit var pokemonService: PokemonService


    suspend fun getPokemon(
        name: String
    ): Response<PokemonApiResponse> {
        return pokemonService.getPokemon(name)
    }

}