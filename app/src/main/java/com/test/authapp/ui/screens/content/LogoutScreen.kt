package com.test.authapp.ui.screens.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.test.authapp.R
import com.test.authapp.db.datastore.DataStorePreferenceCache.Companion.IS_AUTHENTICATED
import com.test.authapp.db.datastore.PreferenceCache
import com.test.authapp.ui.common.customComposableViews.NormalButton
import com.test.authapp.ui.screens.NavigationRoutes
import com.test.authapp.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun LogOutScreen(navController: NavController, preferenceCache: PreferenceCache) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NormalButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = AppTheme.dimens.paddingLarge),
            text = stringResource(id = R.string.log_out),
            onClick = {
                navController.navigate(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                    popUpTo(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        inclusive = true
                    }
                }
                coroutineScope.launch {
                    preferenceCache.putBoolean(IS_AUTHENTICATED, false)
                }
            }
        )
    }
}