package care.intouch.app.core.navigation.navhost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import care.intouch.app.core.navigation.MainNav
import care.intouch.app.core.navigation.PinCodeChange
import care.intouch.app.core.navigation.PinCodeConfirm
import care.intouch.app.core.navigation.ProfileRouteBranch
import care.intouch.app.core.navigation.SuccessfulPinCodeChange
import care.intouch.app.feature.pinCode.ui.confirm.PinCodeConfirmationScreen
import care.intouch.app.feature.pinCode.ui.install.PinCodeInstallationScreen
import care.intouch.app.feature.profile.presentation.ui.PinCodeChangeScreen
import care.intouch.app.feature.profile.presentation.ui.PinCodeConfirmScreen
import care.intouch.app.feature.profile.presentation.ui.SuccessfulPinCodeChangeScreen

fun NavGraphBuilder.addNestedProfileGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = PinCodeChange.route, route = ProfileRouteBranch.route
    ) {

        composable(route = PinCodeChange.route) {
            PinCodeChangeScreen(
                pinCodeEntered = {
                    navController.navigate(route = PinCodeConfirm.route)
                },
                exit = {
                    navController.navigate(route = MainNav.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = PinCodeConfirm.route) {
            PinCodeConfirmScreen(
                pinCodeConfirm = {
                    navController.navigate(route = SuccessfulPinCodeChange.route)
                },
                exit = {
                    navController.navigate(route = MainNav.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = SuccessfulPinCodeChange.route) {
            SuccessfulPinCodeChangeScreen(
                onBackToHome = {
                    navController.navigate(route = MainNav.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}