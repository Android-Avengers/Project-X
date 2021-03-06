package io.velvetcreek.projectx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.velvetcreek.projectx.Network.Constants.CHUCK_NORRIS_API_BASE_URL
import io.velvetcreek.projectx.Network.Constants.POKEMON_API_BASE_URL
import io.velvetcreek.projectx.Network.IApiService
import io.velvetcreek.projectx.Network.IChuckNorrisService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .apply { HttpLoggingInterceptor.Level.BODY }
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesBaseRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @Provides
    @Singleton
    @Named("PokemonRetrofit")
    fun providesPokemonRetrofitClient(retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(POKEMON_API_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonService(@Named("PokemonRetrofit") retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesChuckNorrisRetrofitClient(retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CHUCK_NORRIS_API_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckNorrisService(retrofit: Retrofit): IChuckNorrisService {
        return retrofit.create(IChuckNorrisService::class.java)
    }
}