package com.example.langswap.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.langswap.ui.screens.auth.signIn.SignInScreen
import com.example.langswap.ui.screens.auth.signUp.SignUpScreen
import com.example.langswap.ui.screens.auth.start.StartScreen
import com.example.langswap.ui.screens.home.HomeScreen
import com.example.langswap.ui.screens.profile.ProfileEditScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Start.route,
        modifier = modifier
    ) {
        composable(NavigationItem.Start.route) {
            StartScreen(
                onLoginClick = { navController.navigate(NavigationItem.SignIn.route) },
                onSignUpClick = { navController.navigate(NavigationItem.SignUp.route) }
            )
        }
        composable(NavigationItem.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate(NavigationItem.ProfileEdit.route) }
            )
        }
        composable(NavigationItem.SignIn.route) {
            SignInScreen(
                onSignInSuccess = {navController.navigate(NavigationItem.Home.route)}
            )
        }
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.ProfileEdit.route){
            ProfileEditScreen()
        }
    }
}