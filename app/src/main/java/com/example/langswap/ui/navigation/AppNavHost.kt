package com.example.langswap.ui.navigation

import MatchScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.langswap.ui.screens.auth.signIn.SignInScreen
import com.example.langswap.ui.screens.auth.signUp.SignUpScreen
import com.example.langswap.ui.screens.auth.start.StartScreen
import com.example.langswap.ui.screens.chat.chat.ChatScreen
import com.example.langswap.ui.screens.chat.chatList.ChatListScreen
import com.example.langswap.ui.screens.profile.profileEdit.EditProfileScreen
import com.example.langswap.ui.screens.profile.profileInfo.PublicProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
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
                onSignInSuccess = {
                    navController.navigate(NavigationItem.Match.route){
                        popUpTo(NavigationItem.Start.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(NavigationItem.Match.route) {
            MatchScreen(navController = navController)
        }

        composable(NavigationItem.ProfileEdit.route){
            EditProfileScreen(navController = navController)
        }
        composable(
            route = NavigationItem.PublicProfile.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
            PublicProfileScreen(
                userId = userId,
                navController = navController
            )
        }

        composable(NavigationItem.ChatList.route) {
            ChatListScreen(navController = navController)
        }
        composable(
            route = NavigationItem.Chat.route,
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatScreen(
                chatId = chatId,
                navController = navController
            )
        }

    }
}