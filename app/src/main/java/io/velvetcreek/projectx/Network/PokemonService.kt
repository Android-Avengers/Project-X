package io.velvetcreek.projectx.Network

import io.velvetcreek.projectx.Model.PokemonApiResponse
import io.velvetcreek.projectx.Network.Constants.POKEMON_API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PokemonService {

    private var retrofitClient: Retrofit
    private var apiService: IApiService

    init {
        retrofitClient =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkhttpClient())
                .baseUrl(POKEMON_API_BASE_URL)
                .build()

        apiService = retrofitClient.create(IApiService::class.java)
    }

    private fun getOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .apply { HttpLoggingInterceptor.Level.BODY }
            .cache(null)
            .addInterceptor(loggingInterceptor)
        return client.build()
    }

    suspend fun getPokemon(name: String): Response<PokemonApiResponse> {
        return apiService.getPokemon(name)
    }


}
