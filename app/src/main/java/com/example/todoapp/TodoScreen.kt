package com.example.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            is TodoUiState.Loading -> LoadingView()
            is TodoUiState.Success -> TodoList(s.todos)
            is TodoUiState.Error -> ErrorView(s.message, onRetry = viewModel::loadTodos)
        }
    }
}

@Composable
fun LoadingView() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun TodoList(todos: List<Todo>) {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        items(todos) { todo ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = todo.completed, onCheckedChange = null)
                Text(text = todo.title, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
