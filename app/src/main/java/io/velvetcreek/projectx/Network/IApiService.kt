package io.velvetcreek.projectx.Network

import io.velvetcreek.projectx.Model.PokemonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String,
    ): Response<PokemonApiResponse>
}