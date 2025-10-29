package com.example.week02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week02.ui.theme.NewAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    Column(modifier = Modifier.padding(16.dp)) {

        Text("1부터 5까지:")
        for (i in 1..5) {
            Text("- $i")
        }

        Text("\n1부터 10까지 홀수:")
        for (i in 1..10 step 2) {
            Text("- $i")
        }

        Text("\n5부터 1까지 감소:")
        for (i in 5 downTo 1) {
            Text("- $i")
        }

        Text("\n1부터 5 전까지 (until):")
        for (i in 1 until 5) {
            Text("- $i")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NewAppTheme {
        Main()
    }
}
