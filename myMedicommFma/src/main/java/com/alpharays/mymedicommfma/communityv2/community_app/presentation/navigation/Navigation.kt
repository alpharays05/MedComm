package com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.alpharays.medico.medico_utils.MedicoUtils
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.AddNewCommunityPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityFullPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostCommentsSharedViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message.DirectMessageScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox.MessageInboxScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val postCommentsSharedViewModel: PostCommentsSharedViewModel = viewModel()
    val isInternetAvailable = MedicoUtils.isInternetAvailable(context)

    val startDestination = CommunityAppScreens.CommunityScreen.route
    NavHost(navController = navController, startDestination = startDestination) {
    }


}