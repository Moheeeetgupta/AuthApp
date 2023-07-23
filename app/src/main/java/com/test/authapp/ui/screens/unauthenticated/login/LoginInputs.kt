package com.test.authapp.ui.screens.unauthenticated.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.test.authapp.ui.common.customComposableViews.NormalButton
import com.test.authapp.ui.common.customComposableViews.PasswordTextField
import com.test.authapp.ui.screens.unauthenticated.login.state.LoginState
import com.test.authapp.ui.theme.AppTheme
import com.test.authapp.R
import com.test.authapp.ui.common.customComposableViews.EmailTextField

@Composable
fun LoginInputs(
    loginState: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {

    // Login Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email Id
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = loginState.email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.enter_email_id),
            isError = loginState.errorState.emailErrorState.hasError,
            errorText = stringResource(id = loginState.errorState.emailErrorState.errorMessageStringResource)
        )


        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = loginState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.login_password_label),
            isError = loginState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = loginState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

        // Login Submit Button
        NormalButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = AppTheme.dimens.paddingLarge),
            text = stringResource(id = R.string.login_button_text),
            onClick = onSubmit
        )

    }
}