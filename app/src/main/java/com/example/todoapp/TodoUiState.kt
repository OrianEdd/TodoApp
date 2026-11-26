package com.example.todoapp

sealed interface TodoUiState {
    data object Loading : TodoUiState
    data class Success(
        val todos: List<Todo>
    ) : TodoUiState
    data class Error(
        val message: String
    ) : TodoUiState
}