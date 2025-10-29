package com.example.week03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week03.ui.theme.NewAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Compose Coffee")
        Image(
            contentDescription = "My Image",
            painter = painterResource(id = R.drawable.compose),
            modifier = Modifier
                .size(size = 100.dp)
                .padding(vertical = 16.dp)
        )
        Text(text = "위치는 우송대 정문 앞")

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewAppTheme {
        HomeScreen()
    }
}
