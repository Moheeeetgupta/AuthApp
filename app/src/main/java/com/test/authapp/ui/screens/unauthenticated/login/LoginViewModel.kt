package com.test.authapp.ui.screens.unauthenticated.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.authapp.repository.AuthRepositoryInterface
import com.test.authapp.ui.common.state.ErrorState
import com.test.authapp.ui.screens.unauthenticated.login.state.LoginErrorState
import com.test.authapp.ui.screens.unauthenticated.login.state.LoginState
import com.test.authapp.ui.screens.unauthenticated.login.state.LoginUiEvent
import com.test.authapp.ui.screens.unauthenticated.login.state.emailEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.login.state.emailNotAvailableErrorState
import com.test.authapp.ui.screens.unauthenticated.login.state.passwordEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.login.state.passwordNotCorrectErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for Login Screen
 */
class LoginViewModel(
    private val authRepositoryInterface: AuthRepositoryInterface,
) : ViewModel() {

    var loginState = mutableStateOf(LoginState())
        private set

    /**
     * Function called on any login event [LoginUiEvent]
     */
    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            // Email changed
            is LoginUiEvent.EmailChanged -> {
                loginState.value = loginState.value.copy(
                    email = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            // Submit Login
            is LoginUiEvent.Submit -> {
                if (validateInputs()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = authRepositoryInterface.getUserInfo(loginState.value.email)
                        loginState.value = if (result.isNullOrEmpty()) {
                            loginState.value.copy(
                                errorState = loginState.value.errorState.copy(
                                    emailErrorState = emailNotAvailableErrorState
                                )
                            )
                        } else if (result != loginState.value.password) {
                            loginState.value.copy(
                                errorState = loginState.value.errorState.copy(
                                    passwordErrorState = passwordNotCorrectErrorState
                                )
                            )
                        } else {
                            loginState.value.copy(isLoginSuccessful = true)
                        }
                    }
                }
            }
        }
    }

    /**
     * Function to validate inputs
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private fun validateInputs(): Boolean {
        val emailString = loginState.value.email.trim()
        val passwordString = loginState.value.password
        return when {

            // Email empty
            emailString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }

}