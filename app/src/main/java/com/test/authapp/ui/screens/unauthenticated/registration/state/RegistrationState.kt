package com.test.authapp.ui.screens.unauthenticated.registration.state

import com.test.authapp.ui.common.state.ErrorState

/**
 * Registration State holding ui input values
 */
data class RegistrationState(
    val emailId: String = "",
    val userName: String = "",
    val password: String = "",
    val countryName: String = "",
    val confirmPassword: String = "",
    val errorState: RegistrationErrorState = RegistrationErrorState(),
    val isRegistrationSuccessful: Boolean = false
)

/**
 * Error state in registration holding respective
 * text field validation errors
 */
data class RegistrationErrorState(
    val emailIdErrorState: ErrorState = ErrorState(),
    val userNameErrorState: ErrorState = ErrorState(),
    val countryNameErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)