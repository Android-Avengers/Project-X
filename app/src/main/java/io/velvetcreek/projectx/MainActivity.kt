package io.velvetcreek.projectx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import io.velvetcreek.persistence.AppDatabase
import io.velvetcreek.projectx.Network.IApiService
import io.velvetcreek.projectx.Network.IChuckNorrisService
import io.velvetcreek.projectx.repository.ChuckNorrisRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
// Note: Can't use constructor injection for activity
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var pokemonService: IApiService
    @Inject
    lateinit var chuckNorrisService: IChuckNorrisService

    // TODO: DI
    val db by lazy {
            Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
                "chuckNorrisDb"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
//            Timber.d("${PokemonRepository(pokemonService).getPokemon("pikachu").body()}")

            val data = ChuckNorrisRepository(chuckNorrisService).getJoke().data
            Timber.d("$data")
            data?.let {
                db.jokeDao().insertJoke(data)
            }
            Timber.d("jokes---------> ${db.jokeDao().getAllJokes()}")
        }
    }
}