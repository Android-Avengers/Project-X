package io.velvetcreek.projectx.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.velvetcreek.projectx.ui.activity.ui.theme.ProjectXTheme

class FlowCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App {
                Content()
            }
        }
    }
}

// We don't return UI composables
// Compose apps transform data into UI by calling composable functions
@Composable
fun App(content: @Composable () -> Unit) {
    ProjectXTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
           content()
        }
    }
}

// State Hoisting: Move the internal state higher to the caller of the function
@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(onClick = { updateCount(count + 1) } ) {
        Text(text = "Clicked $count")
    }

}

@Composable
fun Content(names: List<String> = List(1000) { "Hello Android $it" }) {
    var counterState by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier.weight(1f)) {
            NamesList(names = names, modifier = Modifier.weight(1f))
        }
        Counter(
            count = counterState,
            updateCount = { newCount ->
                counterState = newCount
            }
        )
        if (counterState > 5) {
            Text(text = "I love to count!")
        }
    }
}

@Composable
fun NamesList(names: List<String>, modifier: Modifier = Modifier) {
    // Recycler View
    // LazyColumn does not recycle children, but emits new composables as you scroll through
    // Performant because emitting views are relatively cheap
    LazyColumn(modifier = modifier) {
        items(items = names) {
            Greeting(name = it)
            Divider()
        }
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    // by is a delegate that makes things easier
    // Using MaterialTheme will auto change text values and respond to light/dark mode changes
    val targetColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
        animationSpec = tween(durationMillis = 4000)
    )
    Surface(color = targetColor) {
        Text(
            text = name,
            modifier = Modifier
                .clickable { isSelected = !isSelected }
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App {
        Content()
    }
}