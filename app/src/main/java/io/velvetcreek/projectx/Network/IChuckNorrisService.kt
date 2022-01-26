package io.velvetcreek.projectx.Network

import io.velvetcreek.projectx.Model.chuckNorris.Joke
import retrofit2.Response
import retrofit2.http.GET

interface IChuckNorrisService {
    @GET("random")
    suspend fun getJoke(): Response<Joke>
}