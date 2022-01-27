package io.velvetcreek.projectx.Network

import io.velvetcreek.projectx.Model.chuckNorris.Joke
import io.velvetcreek.projectx.util.Resource

interface IChuckNorrisRepository {
    suspend fun getJoke(): Resource<Joke>
}