package com.example.cityguide.main.contract

sealed interface MainContract {
    class UiState

    sealed interface UiAction {

    }

    sealed interface SideEffect {

    }
}
