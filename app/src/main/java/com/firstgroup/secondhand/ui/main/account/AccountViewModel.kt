package com.firstgroup.secondhand.ui.main.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.domain.auth.GetUserUseCase
import com.firstgroup.secondhand.domain.auth.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AccountUiState> = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> get() = _uiState.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            when (val result = getUserUseCase(Unit)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(recentUser = result.data)
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(error = result.exception?.message.toString())
                    }
                }
            }
        }
    }

    fun updateUser(
        fullName: String,
        phoneNo: String,
        address: String,
        city: String
    ) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = true)
        }
        val newUserData = AuthUserRequest(
            fullName = fullName,
            email = "husni18@mail.com",
            password = "123456",
            phoneNo = phoneNo,
            address = address,
            city = city,
            image = uiState.value.image
        )
        Log.d("updateuser", "executed")
        viewModelScope.launch {
            when (val result = updateUserUseCase(newUserData)) {
                is Result.Success -> {
                    Log.d("updateuser", result.data.profilePicture.toString())
                    _uiState.update {
                        it.copy(isSuccess = true)
                    }
                }
                is Result.Error -> {
                    Log.d("updateuser", result.exception?.message.toString())
                    _uiState.update {
                        it.copy(error = result.exception?.message.toString())
                    }
                }
            }
        }
    }

    fun setImage(imageFile: File) {
        _uiState.update {
            it.copy(image = imageFile)
        }
    }
}

data class AccountUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val recentUser: User? = null,
    val image: File? = null
)