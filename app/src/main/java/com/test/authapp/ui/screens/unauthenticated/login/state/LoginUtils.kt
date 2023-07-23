package com.test.authapp.ui.screens.unauthenticated.login.state

import com.test.authapp.R
import com.test.authapp.ui.common.state.ErrorState

val emailEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_email
)
val emailNotAvailableErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_email_not_available
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)

val passwordNotCorrectErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_password_not_correct
)