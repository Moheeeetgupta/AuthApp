package com.test.authapp.utility

import android.util.Patterns
import com.test.authapp.ui.common.state.ErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.emailEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.inValidEmailEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordDigitMissingErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordEmptyErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordLowerCaseCharMissingErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordSizeLesserErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordSpecialCharMissingErrorState
import com.test.authapp.ui.screens.unauthenticated.registration.state.passwordUpperCaseCharMissingErrorState

object FieldUtils {
    fun emailValidity(emailId: String): ErrorState {
        return when {
            emailId.isEmpty() -> emailEmptyErrorState

            Patterns.EMAIL_ADDRESS.matcher(emailId).matches().not() -> inValidEmailEmptyErrorState

            else -> ErrorState()

        }
    }
    fun passwordValidity(password: String): ErrorState {
        return when {
            password.isEmpty() -> passwordEmptyErrorState
            password.length < 8 -> passwordSizeLesserErrorState
            password.containsSpecialChar().not() -> passwordSpecialCharMissingErrorState
            password.containsLowerCaseLetters().not() -> passwordLowerCaseCharMissingErrorState
            password.containsUpperCaseLetters().not() -> passwordUpperCaseCharMissingErrorState
            password.containsDigit().not() -> passwordDigitMissingErrorState
            else -> ErrorState()
        }
    }

    private fun String.containsSpecialChar(): Boolean {
        val specialChars = "[!@#$%&()]"
        specialChars.forEach { specialChar ->
            if (this.contains(specialChar)) {
                return true
            }

        }
        return false
    }

    private fun String.containsLowerCaseLetters(): Boolean {
        this.forEach {
            if (it.isLowerCase()) {
                return true
            }
        }
        return false
    }

    private fun String.containsUpperCaseLetters(): Boolean {
        this.forEach {
            if (it.isUpperCase()) {
                return true
            }
        }
        return false
    }
    private fun String.containsDigit(): Boolean{
        this.forEach {
            if(it.isDigit()){
                return true
            }
        }
        return false
    }

}