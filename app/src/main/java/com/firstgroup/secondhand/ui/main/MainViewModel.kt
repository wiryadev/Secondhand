package com.firstgroup.secondhand.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import com.firstgroup.secondhand.domain.auth.SetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val setTokenUseCase: SetTokenUseCase,
) : ViewModel() {

    fun getSession() {
        viewModelScope.launch {
            getSessionUseCase(Unit).collect { result ->
                when (result) {
                    is Result.Error -> {
                        // do nothing
                    }
                    is Result.Success -> {
                        val token = result.data.token
                        setTokenUseCase(token)
                    }
                }
            }
        }
    }

}