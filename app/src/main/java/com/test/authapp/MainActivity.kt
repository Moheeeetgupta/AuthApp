package com.test.authapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.test.authapp.db.roomdb.AppDatabase
import com.test.authapp.db.datastore.DataStorePreferenceCache
import com.test.authapp.db.datastore.DataStorePreferenceCache.Companion.IS_AUTHENTICATED
import com.test.authapp.db.datastore.PreferenceCache
import com.test.authapp.repository.AuthRepositoryImpl
import com.test.authapp.repository.AuthRepositoryInterface
import com.test.authapp.ui.screens.NavigationRoutes
import com.test.authapp.ui.screens.authenticatedGraph
import com.test.authapp.ui.screens.unauthenticatedGraph
import com.test.authapp.ui.theme.ComposeLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authRepository: AuthRepositoryInterface =
            AuthRepositoryImpl(AppDatabase.getDbInstance(this).getUserInfoDao())
        val preferenceCache: PreferenceCache = DataStorePreferenceCache(this)
        setContent {
            ComposeLoginTheme {
                MainApp(authRepository, preferenceCache)
            }
        }
    }
}

@Composable
fun MainApp(authRepository: AuthRepositoryInterface, preferenceCache: PreferenceCache) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainAppNavHost(authRepository = authRepository, preferenceCache = preferenceCache)
    }

}

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authRepository: AuthRepositoryInterface,
    preferenceCache: PreferenceCache
) {
    val (isAuthenticated, setIsAuthenticated) = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        preferenceCache.getBoolean(IS_AUTHENTICATED).collect {
            setIsAuthenticated(it)
        }
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isAuthenticated) {
            NavigationRoutes.Authenticated.NavigationRoute.route
        } else {
            NavigationRoutes.Unauthenticated.NavigationRoute.route
        }
    ) {
        // Unauthenticated user flow screens
        unauthenticatedGraph(
            navController = navController,
            authRepository = authRepository,
            preferenceCache = preferenceCache
        )

        // Authenticated user flow screens
        authenticatedGraph(navController = navController, preferenceCache = preferenceCache)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLoginTheme {
        MainApp(
            AuthRepositoryImpl(AppDatabase.getDbInstance(LocalContext.current).getUserInfoDao()),
            DataStorePreferenceCache(LocalContext.current)
        )
    }
}