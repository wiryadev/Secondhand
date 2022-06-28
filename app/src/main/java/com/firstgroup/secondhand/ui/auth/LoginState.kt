package com.firstgroup.secondhand.ui.auth

sealed interface LoginState {
    object Idle : LoginState
    data class Loaded(
        val isLoggedIn: Boolean,
    ): LoginState
}