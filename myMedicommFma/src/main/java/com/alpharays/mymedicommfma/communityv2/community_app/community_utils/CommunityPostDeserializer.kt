package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.Reactions
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CommunityPostDeserializer : JsonDeserializer<CommunityPost> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): CommunityPost {
        val jsonObject = json.asJsonObject

        val v = jsonObject.get("__v")?.asString
        val id = jsonObject.get("_id")?.asString
        val aboutDoc = jsonObject.get("aboutDoc")?.asString
        val avatar = jsonObject.get("avatar")?.asString
        val postContent = jsonObject.get("postContent")?.asString
        val postTitle = jsonObject.get("postTitle")?.asString
        val posterId = jsonObject.get("posterId")?.asString
        val posterName = jsonObject.get("posterName")?.asString

        val commentsType = object : TypeToken<List<String>>() {}.type
        val comments = context.deserialize<List<String>>(jsonObject.get("comments"), commentsType)

        val reactionsType = object : TypeToken<Reactions>() {}.type
        val reactions = context.deserialize<Reactions>(jsonObject.get("reactions"), reactionsType)

        return CommunityPost(
            v = v,
            id = id,
            aboutDoc = aboutDoc,
            avatar = avatar,
            comments = comments,
            postContent = postContent,
            postTitle = postTitle,
            posterId = posterId,
            posterName = posterName,
            reactions = reactions
        )
    }
}

