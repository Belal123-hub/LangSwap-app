package com.example.langswap.ui.navigation

// ui/navigation/NavigationItem.kt
sealed class NavigationItem(val route: String) {
    object Start : NavigationItem("start")
    object SignUp : NavigationItem("sign_up")
    object SignIn : NavigationItem("sign_in")
    object  ProfileEdit : NavigationItem("profile_edit")
    object Match : NavigationItem("match")
    object ChatList : NavigationItem("chat_list")
    object PublicProfile : NavigationItem("public_profile/{userId}") {
        fun createRoute(userId: String) = "public_profile/$userId"
    }
    object Chat : NavigationItem("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
}