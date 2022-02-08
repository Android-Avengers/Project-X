package io.velvetcreek.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.velvetcreek.flow.ui.theme.ProjectXTheme
import io.velvetcreek.flow.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectXTheme {

            }
        }
    }
}

@Composable
fun Body(name: String) {
    val viewModel = viewModel<MainViewModel>()
    val time = viewModel.countdownFlow.collectAsState(initial = 10)

    // A surface container using the 'background' color from the theme
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            time.value.toString(),
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
//    Text("What is a flow?")
//    Text("A flow is a coroutine that can emit multiple values over a period of time")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjectXTheme {
        Body("Android")
    }
}