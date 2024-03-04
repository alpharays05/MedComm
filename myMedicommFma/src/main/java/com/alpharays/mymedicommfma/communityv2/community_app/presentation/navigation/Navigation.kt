package com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostCommentsSharedViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val postCommentsSharedViewModel: PostCommentsSharedViewModel = viewModel()
    val isInternetAvailable = CommunityUtils.isInternetAvailable(context)

    val startDestination = CommunityAppScreens.CommunityScreen.route
    NavHost(navController = navController, startDestination = startDestination) {
    }


}