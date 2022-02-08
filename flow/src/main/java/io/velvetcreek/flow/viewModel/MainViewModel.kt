package io.velvetcreek.flow.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    // TODO: Code highlighter
    // Cold flow
    val countdownFlow = flow<Int> {
        // Emit values over time, essentially treat this as a coroutine
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while(currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        // Alt way
//        countdownFlow.onEach {
//            println(it)
//        }.launchIn(viewModelScope)

        viewModelScope.launch {
            val count = countdownFlow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                   time * time
                }
                .onEach { time ->
                    println(time)
                }
                .count { // terminal operator
                    it % 2 == 0
                }
            println("The count is $count")
//                .collect { time -> // collectLatest to only get the latest values, collect is also a terminal operator
//                    println("The current time is $time")
//                }
        }

    }
}