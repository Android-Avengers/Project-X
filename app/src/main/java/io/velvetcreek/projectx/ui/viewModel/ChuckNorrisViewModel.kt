package io.velvetcreek.projectx.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.velvetcreek.projectx.Model.chuckNorris.Joke
import io.velvetcreek.projectx.repository.ChuckNorrisRepository
import io.velvetcreek.projectx.util.DispatcherProvider
import io.velvetcreek.projectx.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChuckNorrisViewModel @Inject constructor(
    private val chuckNorrisRepository: ChuckNorrisRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    fun getJoke() {
        viewModelScope.launch(dispatchers.io) {
            _joke.value = JokeEvent.Loading
            when(val response = chuckNorrisRepository.getJoke()) {
                is Resource.Error -> _joke.value = JokeEvent.Failure(response.message ?: "Request failed")
                is Resource.Success -> {
                    response.data?.let {
                        _joke.value = JokeEvent.Success(it)
                    }
                }
            }
        }
    }

    sealed class JokeEvent {
        class Success(val result: Joke): JokeEvent()
        class Failure(val errorText: String): JokeEvent()
        object Loading: JokeEvent()
        object Empty: JokeEvent()
    }

    private val _joke = MutableStateFlow<JokeEvent>(JokeEvent.Empty)
    val joke: StateFlow<JokeEvent> = _joke
}