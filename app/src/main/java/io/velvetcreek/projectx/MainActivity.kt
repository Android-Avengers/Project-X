package io.velvetcreek.projectx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.velvetcreek.projectx.Network.IApiService
import io.velvetcreek.projectx.Network.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

import timber.log.Timber.*
import timber.log.Timber.Forest.plant


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var pokemonService: IApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            Timber.d("${PokemonRepository(pokemonService).getPokemon("pikachu").body()}")
        }
    }
}