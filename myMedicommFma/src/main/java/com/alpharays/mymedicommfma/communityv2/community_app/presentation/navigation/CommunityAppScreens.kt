package com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation

import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_NEW_POST_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENTS_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMUNITY_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.DIRECT_MESSAGE_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.MESSAGE_INBOX_SCREEN_ROUTE


sealed class CommunityAppScreens(val route: String) {
     data object CommunityScreen : CommunityAppScreens(COMMUNITY_SCREEN_ROUTE)
     data object AddNewCommunityPostScreen : CommunityAppScreens(ADD_NEW_POST_SCREEN_ROUTE)
     data object CommunityPostScreen : CommunityAppScreens(COMMENTS_SCREEN_ROUTE)
     data object DirectMessageScreen : CommunityAppScreens(DIRECT_MESSAGE_SCREEN_ROUTE)
     data object MessageInboxScreen : CommunityAppScreens(MESSAGE_INBOX_SCREEN_ROUTE)
}