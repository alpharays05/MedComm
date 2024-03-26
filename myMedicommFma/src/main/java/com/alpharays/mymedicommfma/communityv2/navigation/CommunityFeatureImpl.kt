package com.alpharays.mymedicommfma.communityv2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alpharays.mymedicommfma.communityv2.CommunityFeatureApi
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.global_search.GlobalCommunitySearch
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.AddNewCommunityPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityFullPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostSharedViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message.DirectMessageScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox.MessageInboxScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens

private const val baseRoute = "community_screen"
private const val commRouteScenario = "$baseRoute/scenario"

class CommunityFeatureImpl : CommunityFeatureApi {
    override val communityRoute: String
        get() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        // TODO: Replace your existing NavHost with AnimatedNavHost. You'll need to define the animations for each navigation action.
        navGraphBuilder.composable(baseRoute) {
            CommunityScreen(navController = navController)
        }
        navGraphBuilder.navigation(
            route = commRouteScenario,
            startDestination = CommunityAppScreens.AddNewCommunityPostScreen.route
        ) {
            composable(route = CommunityAppScreens.GlobalCommunitySearch.route) {
                GlobalCommunitySearch(navController = navController)
            }

            composable(route = CommunityAppScreens.AddNewCommunityPostScreen.route) {
                AddNewCommunityPostScreen(navController = navController)
            }

            composable(
                route = CommunityAppScreens.CommunityFullPostScreen.route + "/{currentPostId}",
                arguments = listOf(
                    navArgument("currentPostId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                // for getting all comments
                val context = LocalContext.current
                val postId = entry.arguments?.getString("currentPostId") ?: ""
                CommunityUtils.setOneTimePostId(context, postId)
                CommunityFullPostScreen(navController = navController)
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

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}