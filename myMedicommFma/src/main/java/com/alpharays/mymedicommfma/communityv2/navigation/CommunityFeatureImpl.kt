package com.alpharays.mymedicommfma.communityv2.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.CommunityFeatureApi
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.AddNewCommunityPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityFullPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostCommentsSharedViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message.DirectMessageScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox.MessageInboxScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens

private const val baseRoute = "community_screen"
private const val commRouteScenario = "$baseRoute/scenario"

class CommunityFeatureImpl(
    private val isInternetAvailable: ConnectivityObserver.Status,
    private val postCommentsSharedViewModel: PostCommentsSharedViewModel?,

    ) : CommunityFeatureApi {
    override val communityRoute: String
        get() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            CommunityScreen(
                navController,
                isInternetAvailable,
                modifier
            )
        }
        navGraphBuilder.navigation(
            route = commRouteScenario,
            startDestination = CommunityAppScreens.AddNewCommunityPostScreen.route
        ) {


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
                if (postCommentsSharedViewModel != null) {
                    CommunityFullPostScreen(
                        navController = navController,
                        isInternetAvailable = isInternetAvailable,
                        postCommentsSharedViewModel = postCommentsSharedViewModel
                    )
                }
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

}