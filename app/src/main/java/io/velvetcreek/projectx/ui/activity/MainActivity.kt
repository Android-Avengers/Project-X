package io.velvetcreek.projectx.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
//import io.flutter.embedding.android.FlutterActivity
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
    private lateinit var auth: FirebaseAuth
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

        // Uncomment after resolving Circle CI and Flutter Issues
//        binding.btn.setOnClickListener {
//            startActivity(
//                FlutterActivity.createDefaultIntent(this)
//            )
//        }

        binding.btnGetJoke.setOnClickListener {
            chuckNorrisViewModel.getJoke()
        }

        // Firebase Auth Setup
        auth = Firebase.auth
        with(binding) {
            btnCreateUser.setOnClickListener {
                Timber.d("${etEmail.text.toString()}, ${etPassword.text.toString()}")
                auth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                    .addOnCompleteListener(this@MainActivity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Timber.d("createUserWithEmail:success")
                            val user = auth.currentUser
                            Timber.d("user: $user")
                            updateUi()
                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.w("createUserWithEmail:failure, ${task.exception?.message}")
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            // TODO: Error handling
            btnSignIn.setOnClickListener {
                auth.signInWithEmailAndPassword("${etEmail.text}", "${etPassword.text}")
                updateUi()
            }
            // TODO: Error handling
            btnSignOut.setOnClickListener {
                auth.signOut()
                updateUi()
            }
        }


//        crashlyticsTest()

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

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Timber.d("preCurrentUser: $currentUser")
        currentUser?.apply {
            Timber.d("Reloading user")
            reload()
        }
        binding.tvUser.text = currentUser?.email
        Timber.d("postCurrentUser: ${currentUser?.email}")
    }

    private fun updateUi() = with(binding) {
        tvUser.text = auth.currentUser?.email ?: ""
    }

    private fun crashlyticsTest() {
        // Doesn't apply theme and adds to top of view
        val crashButton = Button(this).apply {
            text = "Test Crash"
            setOnClickListener {
                throw RuntimeException("Test Crash") // Force a crash
            }
        }
        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        )
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

