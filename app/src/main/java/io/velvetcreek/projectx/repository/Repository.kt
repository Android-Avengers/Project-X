package io.velvetcreek.projectx.repository

import io.velvetcreek.projectx.Model.chuckNorris.Joke
import io.velvetcreek.projectx.Network.IChuckNorrisRepository
import io.velvetcreek.projectx.Network.IChuckNorrisService
import io.velvetcreek.projectx.util.Resource
import javax.inject.Inject

class ChuckNorrisRepository @Inject constructor(
    private val chuckNorrisService: IChuckNorrisService
): IChuckNorrisRepository {
    override suspend fun getJoke(): Resource<Joke> {
        return try {
            val response = chuckNorrisService.getJoke()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error occurred")
        }
    }
}