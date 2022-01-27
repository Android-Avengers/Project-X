package io.velvetcreek.projectx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.velvetcreek.projectx.Network.IChuckNorrisRepository
import io.velvetcreek.projectx.Network.IChuckNorrisService
import io.velvetcreek.projectx.repository.ChuckNorrisRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideChuckNorrisRepository(service: IChuckNorrisService): IChuckNorrisRepository
        = ChuckNorrisRepository(service)
}