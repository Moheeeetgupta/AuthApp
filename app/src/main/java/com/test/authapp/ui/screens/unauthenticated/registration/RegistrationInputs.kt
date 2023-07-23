package com.test.authapp.ui.screens.unauthenticated.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.test.authapp.R
import com.test.authapp.ui.common.customComposableViews.DropDown
import com.test.authapp.ui.common.customComposableViews.EmailTextField
import com.test.authapp.ui.common.customComposableViews.NormalButton
import com.test.authapp.ui.common.customComposableViews.PasswordTextField
import com.test.authapp.ui.common.customComposableViews.UserNameTextField
import com.test.authapp.ui.screens.unauthenticated.registration.state.RegistrationState
import com.test.authapp.ui.theme.AppTheme
import com.test.authapp.utility.JsonUtils.getCountryList

@Composable
fun RegistrationInputs(
    registrationState: RegistrationState,
    onEmailIdChange: (String) -> Unit,
    onUserNameChanged: (String) -> Unit,
    onCountryNameChanged: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    val context = LocalContext.current
    // Registration Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {


        // Email Id
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registrationState.emailId,
            onValueChange = onEmailIdChange,
            label = stringResource(id = R.string.enter_email_id),
            isError = registrationState.errorState.emailIdErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.emailIdErrorState.errorMessageStringResource)
        )

        // UserName
        UserNameTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registrationState.userName,
            onValueChange = onUserNameChanged,
            label = stringResource(id = R.string.username),
            isError = registrationState.errorState.userNameErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.userNameErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )
        DropDown(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            suggestions = getCountryList(context, stringResource(R.string.countries_json)),
            selectedText = registrationState.countryName,
            onValueChange = onCountryNameChanged,
            label = stringResource(R.string.select_country),
            isError = registrationState.errorState.countryNameErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.countryNameErrorState.errorMessageStringResource),
        )

        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registrationState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.registration_password_label),
            isError = registrationState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Next
        )

        // Confirm Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registrationState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = stringResource(id = R.string.registration_confirm_password_label),
            isError = registrationState.errorState.confirmPasswordErrorState.hasError,
            errorText = stringResource(id = registrationState.errorState.confirmPasswordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

        // Registration Submit Button
        NormalButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = AppTheme.dimens.paddingExtraLarge),
            text = stringResource(id = R.string.registration_button_text),
            onClick = onSubmit
        )


    }
}