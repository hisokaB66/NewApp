package com.example.newapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.newapp.ui.theme.NewAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFF8E7)) {

                    AppNavigation()
                }
            }
        }
    }
}


data class Task(val text: String, var completed: Boolean = false)

class TaskViewModel : ViewModel() {
    val tasks = mutableStateListOf<Task>()

    fun addTask(text: String) {
        tasks.add(Task(text))
    }

    fun toggleTask(index: Int, completed: Boolean) {
        tasks[index] = tasks[index].copy(completed = completed)
    }

    val completedTasks: List<Task>
        get() = tasks.filter { it.completed }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(viewModel = viewModel, onCompletedClick = {
                navController.navigate("completed")
            })
        }
        composable("completed") {
            CompletedScreen(viewModel = viewModel, navController = navController)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: TaskViewModel, onCompletedClick: () -> Unit) {
    var newTask by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("DailyToDo \uD83D\uDCD1") })
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = onCompletedClick,
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF800020),
                        contentColor = Color.White)
                ) {
                    Text("완료된 할 일")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("할 일 입력") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (newTask.isNotBlank()) {
                                viewModel.addTask(newTask)
                                newTask = ""
                            }
                            focusManager.clearFocus()
                        }
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newTask.isNotBlank()) {
                            viewModel.addTask(newTask)
                            newTask = ""
                        }
                        focusManager.clearFocus()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF800020),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("추가")
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            viewModel.tasks.forEachIndexed { index, task ->
                var checked by remember { mutableStateOf(task.completed) }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF0F5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        ,
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                colors = CheckboxDefaults.colors(
                                checkedColor = Color.Black,
                                uncheckedColor = Color.Black,
                                checkmarkColor = Color.White),
                                checked = checked,
                                onCheckedChange = {
                                    checked = it
                                    viewModel.toggleTask(index, it)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(task.text)
                        }
                        IconButton(onClick = { viewModel.tasks.removeAt(index) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "삭제")
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedScreen(viewModel: TaskViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("완료된 할 일 ✅") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            val completed = viewModel.completedTasks
            if (completed.isEmpty()) {
                Text("완료된 할 일이 없습니다 😅")
            } else {
                completed.forEach { task ->
                    Card(colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF0F5)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = true, onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(
                                    checkedColor = Color.Black,
                                uncheckedColor = Color.Black,
                                checkmarkColor = Color.White))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(task.text)
                        }
                    }
                }
            }
        }
    }
}
