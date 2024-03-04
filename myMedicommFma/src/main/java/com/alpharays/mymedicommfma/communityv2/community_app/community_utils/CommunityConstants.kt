package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

object CommunityConstants {
    // community screen
    const val ALL_COMMUNITY_POSTS = "doc/alldocsposts"
    const val ADD_NEW_POST = "doc/addpost"
    const val ADD_COMMENT = "doc/addcomment"
    const val UPDATE_COMMENT = "doc/updatecomment"
    const val GET_ALL_REPLIES_ON_COMMENT = "doc/allreplies"
    const val GET_ALL_COMMENTS = "doc/allcomments"

    const val ADD_NEW_CHAT = "/chats/addnewchat"
    const val GET_INBOX_MESSAGES = "/chats/getinboxlist"
    const val GET_ALL_CHATS = "/chats/getallchats"

    // navigation routes
    const val COMMUNITY_SCREEN_ROUTE = "community_screen"
    const val DIRECT_MESSAGE_SCREEN_ROUTE = "direct_message_screen"
    const val MESSAGE_INBOX_SCREEN_ROUTE = "message_inbox_screen"
    const val ADD_NEW_POST_SCREEN_ROUTE = "add_new_community_post_screen"
    const val COMMENTS_SCREEN_ROUTE = "comments_screen"

    // TODO : community app not linked to room db -> only initialized
    const val MEDICO_COMMUNITY_TABLE = "medico_community_table"

    const val LIKE_POST_CD = "Like Post"
    const val LOVE_POST_CD = "Love Post"
    const val CELEBRATE_POST_CD = "Celebrate Post"
    const val SUPPORT_POST_CD = "Support Post"
    const val INSIGHTFUL_POST_CD = "Insightful Post"
    const val FUNNY_POST_CD = "Funny Post"

    // socket events
    const val MESSAGE_REPLY_EVENT = "reply"
    const val SEND_MESSAGE = "send_message"
    const val JOIN_ROOM = "join_room"
    const val MESSAGES_ROOM = "message_room"

    const val LIKE_OPTION = "Like"
    const val COMMENT_OPTION = "Comment"
    const val REPOST_OPTION = "Repost"
    const val SEND_OPTION = "Send"
    const val LIKE_PAINTER_CONTENT_DSC = "Like Image"
    const val COMMENT_PAINTER_CONTENT_DSC = "Comment Image"
    const val REPOST_PAINTER_CONTENT_DSC = "Repost Image"
    const val SEND_PAINTER_CONTENT_DSC = "Send Image"
}