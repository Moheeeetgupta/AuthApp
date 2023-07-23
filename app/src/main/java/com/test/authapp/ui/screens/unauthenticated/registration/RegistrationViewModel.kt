package com.test.authapp.ui.screens.unauthenticated.registration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.authapp.db.roomdb.UserInfoEntity
import com.test.authapp.repository.AuthRepositoryInterface
import com.test.authapp.ui.common.state.ErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.RegistrationErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.RegistrationState
import com.test.authapp.ui.screens.unauthenticated.registration.state.RegistrationUiEvent
import com.test.authapp.ui.screens.unauthenticated.registration.state.confirmPasswordEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.countryNameChangedErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.userNameEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordMismatchErrorState
import com.test.authapp.utility.FieldUtils.emailValidity
import com.test.authapp.utility.FieldUtils.passwordValidity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val repositoryInterface: AuthRepositoryInterface) :
    ViewModel() {

    var registrationState = mutableStateOf(RegistrationState())
        private set

    /**
     * Function called on any login event [RegistrationUiEvent]
     */
    fun onUiEvent(registrationUiEvent: RegistrationUiEvent) {
        when (registrationUiEvent) {

            // Email id changed event
            is RegistrationUiEvent.EmailChanged -> {
                registrationState.value = registrationState.value.copy(
                    emailId = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        emailIdErrorState = emailValidity(registrationUiEvent.inputValue.trim())

                    )
                )
            }

            // UserName changed event
            is RegistrationUiEvent.UserNameChanged -> {
                registrationState.value = registrationState.value.copy(
                    userName = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        userNameErrorState = if (registrationUiEvent.inputValue.trim()
                                .isEmpty()
                        ) {
                            // User Name Empty state
                            userNameEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }
            // CountryName changed event
            is RegistrationUiEvent.CountryNameChanged -> {
                registrationState.value = registrationState.value.copy(
                    countryName = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        countryNameErrorState = if (registrationUiEvent.inputValue.trim()
                                .isEmpty()
                        ) {
                            // Country Name Empty state
                            countryNameChangedErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Password changed event
            is RegistrationUiEvent.PasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    password = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        passwordErrorState = passwordValidity(registrationUiEvent.inputValue.trim())
                    )
                )
            }

            // Confirm Password changed event
            is RegistrationUiEvent.ConfirmPasswordChanged -> {
                registrationState.value = registrationState.value.copy(
                    confirmPassword = registrationUiEvent.inputValue,
                    errorState = registrationState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            registrationUiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            registrationState.value.password.trim() != registrationUiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }


            // Submit Registration event
            is RegistrationUiEvent.Submit -> {
                if (validateInputs()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repositoryInterface.registerUser(
                            UserInfoEntity(
                                emailId = registrationState.value.emailId,
                                userName = registrationState.value.userName,
                                countryName = registrationState.value.countryName,
                                password = registrationState.value.password
                            )
                        )
                        registrationState.value =
                            registrationState.value.copy(isRegistrationSuccessful = true)
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
        val emailString = registrationState.value.emailId.trim()
        val userNameString = registrationState.value.userName.trim()
        val countryNameString = registrationState.value.countryName.trim()
        val passwordString = registrationState.value.password.trim()
        val confirmPasswordString = registrationState.value.confirmPassword.trim()
        val emailValidityErrorState = emailValidity(emailString)
        val passwordValidityErrorState = passwordValidity(passwordString)

        return when {

            // Email validity
            emailValidityErrorState.hasError -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        emailIdErrorState = emailValidityErrorState
                    )
                )
                false
            }

            //User Name Empty
            userNameString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        userNameErrorState = userNameEmptyErrorState
                    )
                )
                false
            }
            //Country Name Empty
            countryNameString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        countryNameErrorState = countryNameChangedErrorState
                    )
                )
                false
            }
            //Password Validity
            passwordValidityErrorState.hasError -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        passwordErrorState = passwordValidityErrorState
                    )
                )
                false
            }

            //Confirm Password Empty
            confirmPasswordString.isEmpty() -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }

            // Password and Confirm Password are different
            passwordString != confirmPasswordString -> {
                registrationState.value = registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = passwordMismatchErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                registrationState.value =
                    registrationState.value.copy(errorState = RegistrationErrorState())
                true
            }
        }
    }
}