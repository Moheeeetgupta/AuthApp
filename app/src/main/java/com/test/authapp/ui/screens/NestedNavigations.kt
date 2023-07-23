package com.test.authapp.ui.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.test.authapp.AuthViewModelFactory
import com.test.authapp.db.datastore.PreferenceCache
import com.test.authapp.repository.AuthRepositoryInterface
import com.test.authapp.ui.screens.content.LogOutScreen
import com.test.authapp.ui.screens.unauthenticated.login.LoginScreen
import com.test.authapp.ui.screens.unauthenticated.login.LoginViewModel
import com.test.authapp.ui.screens.unauthenticated.registration.RegistrationScreen
import com.test.authapp.ui.screens.unauthenticated.registration.RegistrationViewModel

/**
 * Login, registration, forgot password screens nav graph builder
 * (Unauthenticated user)
 */
fun NavGraphBuilder.unauthenticatedGraph(
    navController: NavController,
    authRepository: AuthRepositoryInterface,
    preferenceCache: PreferenceCache
) {

    navigation(
        route = NavigationRoutes.Unauthenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Unauthenticated.Login.route
    ) {

        // Login
        composable(route = NavigationRoutes.Unauthenticated.Login.route) {
            LoginScreen(
                onNavigateToRegistration = {
                    navController.navigate(route = NavigationRoutes.Unauthenticated.Registration.route)
                },
                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = viewModel(factory = AuthViewModelFactory.viewModelFactory {
                    LoginViewModel(authRepository)
                }),
                preferenceCache = preferenceCache
            )
        }

        // Registration
        composable(route = NavigationRoutes.Unauthenticated.Registration.route) {
            RegistrationScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = NavigationRoutes.Authenticated.NavigationRoute.route) {
                        popUpTo(route = NavigationRoutes.Unauthenticated.NavigationRoute.route) {
                            inclusive = true
                        }
                    }
                },
                registrationViewModel = viewModel(factory = AuthViewModelFactory.viewModelFactory {
                    RegistrationViewModel(authRepository)
                }),
                preferenceCache = preferenceCache
            )
        }
    }
}

/**
 * Authenticated screens nav graph builder
 */
fun NavGraphBuilder.authenticatedGraph(
    navController: NavController,
    preferenceCache: PreferenceCache
) {
    navigation(
        route = NavigationRoutes.Authenticated.NavigationRoute.route,
        startDestination = NavigationRoutes.Authenticated.LogOut.route
    ) {
        // LogoutScreen
        composable(route = NavigationRoutes.Authenticated.LogOut.route) {
            LogOutScreen(navController, preferenceCache)
        }
    }
}