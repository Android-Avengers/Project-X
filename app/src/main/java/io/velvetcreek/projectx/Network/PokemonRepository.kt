package io.velvetcreek.projectx.Network

import io.velvetcreek.projectx.Model.pokemon.PokemonApiResponse
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: IApiService
) {
    suspend fun getPokemon(
        name: String
    ): Response<PokemonApiResponse> {
        return pokemonService.getPokemon(name)
    }
}