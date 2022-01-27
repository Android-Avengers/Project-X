package io.velvetcreek.projectx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterActivity
import io.velvetcreek.projectx.persistence.AppDatabase
import io.velvetcreek.projectx.Network.IApiService
import io.velvetcreek.projectx.Network.IChuckNorrisService
import io.velvetcreek.projectx.Network.PokemonRepository
import io.velvetcreek.projectx.databinding.ActivityMainBinding
import io.velvetcreek.projectx.repository.ChuckNorrisRepository
import io.velvetcreek.projectx.ui.viewModel.ChuckNorrisViewModel
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

    private val chuckNorrisViewModel: ChuckNorrisViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btn.setOnClickListener {
            startActivity(
                FlutterActivity.createDefaultIntent(this)
            )
        }

        binding.btnGetJoke.setOnClickListener {
            chuckNorrisViewModel.getJoke()
        }

        lifecycleScope.launchWhenStarted {
            chuckNorrisViewModel.joke.collect { event ->
                when (event) {
                    is ChuckNorrisViewModel.JokeEvent.Success -> {
                        binding.tvJoke.text = event.result.value
                    }
                    is ChuckNorrisViewModel.JokeEvent.Failure -> {
                        binding.tvJoke.text = event.errorText
                    }
                    is ChuckNorrisViewModel.JokeEvent.Loading -> {
                        binding.tvJoke.text = "loading"
                    }
                    else -> Unit
                }
            }
        }
    }

    fun test() {
        CoroutineScope(Dispatchers.IO).launch {
            Timber.d("${PokemonRepository(pokemonService).getPokemon("pikachu").body()}")
            val data = ChuckNorrisRepository(chuckNorrisService).getJoke().data
            Timber.d("$data")
            data?.let {
                db.jokeDao().insertJoke(data)
            }
            Timber.d("jokes---------> ${db.jokeDao().getAllJokes()}")
        }
    }
}

