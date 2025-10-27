package com.example.week05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week05.ui.theme.NewAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme {
                val count = remember { mutableStateOf(0) }
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    CountApp(count)
                    Spacer(modifier = Modifier.height(32.dp))
                    StopWatchApp()

                }
            }
        }
    }
}

@Composable
fun CountApp(count: MutableState<Int>){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Count: ${count.value}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        Row{
            Button(onClick = {count.value++}) { Text("increase") }
            Button(onClick = {count.value = 0}){ Text("Reset")}
        }

    }
}
@Composable
fun StopWatchApp(){
    var timeInMillis by remember {mutableStateOf(1234L)}
    var isRunning by remember {mutableStateOf(false)}

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                delay(10L)
                timeInMillis += 10L
            }
        }
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = formatTime(timeInMillis),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Row{
            Button(onClick = {isRunning = true}) {Text("Start")}
            Button(onClick = {isRunning = false}) {Text("Stop")}
            Button(onClick = {isRunning = false
                timeInMillis = 0L
            }) {Text("Reset")}
        }

    }
}

private fun formatTime(timeInMillis:Long): String {
    val minutes = (timeInMillis/ 1000) / 60
    val seconds = (timeInMillis/1000)  %60
    val millis = (timeInMillis % 1000 ) / 10
    return String.format("%02d:%02d:%02d", minutes, seconds, millis)
}
@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    val count = remember { mutableStateOf(0) }
    CountApp(count)
}

@Preview(showBackground = true)
@Composable
fun StopWatchPreview() {
    StopWatchApp()
}