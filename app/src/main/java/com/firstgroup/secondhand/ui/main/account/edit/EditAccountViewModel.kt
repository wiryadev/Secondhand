package com.firstgroup.secondhand.ui.main.account.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.network.auth.model.UpdateUserRequest
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
class EditAccountViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditAccountUiState> =
        MutableStateFlow(EditAccountUiState())
    val uiState: StateFlow<EditAccountUiState> get() = _uiState.asStateFlow()


    fun updateUser(
        fullName: String, phoneNo: String, address: String, city: String
    ) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = true)
        }
        val newUserData = UpdateUserRequest(
            fullName = fullName,
            phoneNo = phoneNo,
            address = address,
            city = city,
            image = uiState.value.image
        )
        viewModelScope.launch {
            when (val result = updateUserUseCase(newUserData)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isSuccess = true, isLoading = false)
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exception?.message.toString(),
                            isLoading = false,
                        )
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

    fun resetState() {
        // don't null out existing image
        _uiState.update {
            it.copy(
                isLoading = false,
                isSuccess = false,
                errorMessage = null,
            )
        }
    }

}


data class EditAccountUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val image: File? = null,
)