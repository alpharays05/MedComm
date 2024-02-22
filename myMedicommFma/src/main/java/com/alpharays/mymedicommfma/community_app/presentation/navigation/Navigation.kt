package com.alpharays.mymedicommfma.community_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alpharays.mymedicommfma.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.AddNewCommunityPostScreen
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.CommunityFullPostScreen
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.CommunityScreen
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.PostCommentsSharedViewModel
import com.alpharays.mymedicommfma.community_app.presentation.message_screen.direct_message.DirectMessageScreen
import com.alpharays.mymedicommfma.community_app.presentation.message_screen.message_inbox.MessageInboxScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val postCommentsSharedViewModel: PostCommentsSharedViewModel = viewModel()
    val isInternetAvailable =true //todo: check internet connection

    val startDestination = CommunityAppScreens.CommunityScreen.route
    NavHost(navController = navController, startDestination = startDestination) {
        // community screen(s)
        composable(route = CommunityAppScreens.CommunityScreen.route) {
            CommunityUtils.setOneTimePostId("")
            CommunityScreen(
                navController = navController,
                isInternetAvailable = isInternetAvailable,
                postCommentsSharedViewModel = postCommentsSharedViewModel
            )
        }

        composable(route = CommunityAppScreens.AddNewCommunityPostScreen.route) {
            AddNewCommunityPostScreen(
                navController = navController,
                isInternetAvailable = isInternetAvailable
            )
        }

        composable(
            route = CommunityAppScreens.CommunityPostScreen.route + "/{currentPostId}",
            arguments = listOf(
                navArgument("currentPostId") {
                    type = NavType.StringType
                }
            )
        ) {
            val postId = it.arguments?.getString("currentPostId") ?: ""
            CommunityUtils.setOneTimePostId(postId)
            CommunityFullPostScreen(
                navController = navController,
                isInternetAvailable = isInternetAvailable,
                postCommentsSharedViewModel = postCommentsSharedViewModel
            )
        }

        // message screen(s)
        composable(route = CommunityAppScreens.MessageInboxScreen.route) {
            MessageInboxScreen(navController = navController)
        }

        composable(route = CommunityAppScreens.DirectMessageScreen.route) {
            DirectMessageScreen(navController = navController)
        }
    }
}