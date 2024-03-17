package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.global_search.GlobalSearchBox
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.ChatMsg
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import java.util.Calendar

@Composable
fun MessageInboxScreen(navController: NavController) {
    var searchList by remember {
        mutableStateOf<Array<ChatMsg>?>(null)
    }
    var newSearchedList by remember {
        mutableStateOf<Array<ChatMsg>?>(null)
    }
    var originalList by remember {
        mutableStateOf<Array<ChatMsg>?>(null)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 1f))
                .padding(MaterialTheme.spacing.extraSmall)
        ) {
            ComposableMessageInboxSearch(
                navController = navController,
                searchList = searchList,
                onReset = {
                    newSearchedList = originalList
                },
                onNewSearchedList = {
                    newSearchedList = it
                }
            )

            ComposableMessageInboxList(
                navController = navController,
                newSearchedList = newSearchedList,
                onInboxListReceived = {
                    searchList = it
                    originalList = it
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableMessageInboxSearch(
    searchList: Array<ChatMsg>?,
    onReset: () -> Unit,
    onNewSearchedList: (Array<ChatMsg>) -> Unit,
    navController: NavController,
) {
    var messageSearchText by remember {
        mutableStateOf("")
    }
    var searchBarActive by remember {
        mutableStateOf(false)
    }

    val messageSearchItems = remember {
        mutableStateListOf<ChatMsg>()
    }

    val messageSearchHistoryItems = remember {
        mutableStateListOf<ChatMsg>()
    }

    var searchedChatMsg by remember {
        mutableStateOf<ChatMsg?>(null)
    }

    GlobalSearchBox(navController)
}

@Composable
fun ComposableMessageInboxList(
    navController: NavController,
    newSearchedList: Array<ChatMsg>? = emptyArray(),
    onInboxListReceived: (Array<ChatMsg>) -> Unit,
) {
    val inboxList: ArrayList<ChatMsg> = ArrayList()
    val time = Calendar.getInstance().time
    val chat1 =
        ChatMsg(
            "01",
            "",
            "Hello how are you?",
            time.toString(),
            "1"
        )
    val chat2 =
        ChatMsg(
            "02",
            "",
            "My name is Shivang",
            time.toString(),
            "2"
        )
    val chat3 =
        ChatMsg(
            "04",
            "",
            "What's with this behavior?",
            time.toString(),
            "3"
        )
    val chat4 =
        ChatMsg(
            "05",
            "",
            "You must pass this exam",
            time.toString(),
            "4"
        )
    val chat5 =
        ChatMsg(
            "06",
            "",
            "Certain people say how are you?",
            time.toString(),
            "5"
        )
    val chat6 =
        ChatMsg(
            "07",
            "",
            "But I tried and couldn't resolve it?",
            time.toString(),
            "6"
        )

    inboxList.add(chat1)
    inboxList.add(chat2)
    inboxList.add(chat3)
    inboxList.add(chat4)
    inboxList.add(chat5)
    inboxList.add(chat6)

    var list = inboxList.toTypedArray()

    onInboxListReceived(list)

    if (!newSearchedList.isNullOrEmpty()) {
        list = newSearchedList
    }

    LazyColumn(
        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {
        items(list) {
            ComposableMessageBoxCard(it, navController)
        }
    }
}

@Composable
fun ComposableMessageBoxCard(chatMsg: ChatMsg, navController: NavController) {
    val style1 = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontFamily = manRopeFontFamily,
        color = MaterialTheme.colorScheme.surface
    )
    val style2 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        color = MaterialTheme.colorScheme.surface
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color.White, RoundedCornerShape(MaterialTheme.spacing.extraSmall))
            .padding(MaterialTheme.spacing.extraSmall)
            .clickable {
                navController.navigate(CommunityAppScreens.DirectMessageScreen.route)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)
        ),
        shape = RoundedCornerShape(MaterialTheme.spacing.small)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp)
        ) {
            val context = LocalContext.current
            val painter = painterResource(id = R.drawable.doctor_profile)
            val color = CommunityUtils.getMedicoColor(context, R.color.bluish_gray)
            Image(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(MaterialTheme.size.large)
                    .border(1.dp, Color(color), RoundedCornerShape(MaterialTheme.size.defaultIconSize))
                    .clip(RoundedCornerShape(MaterialTheme.size.defaultIconSize)),
                painter = painter,
                contentDescription = "User avatar"
            )
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp)
            ) {
                Text(
                    text = "Dr Shivang Gautam",
                    style = style1
                )
                Text(
                    text = chatMsg.lastMessage ?: "NA",
                    style = style1.copy(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.W500
                    )
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.extraSmall),
                    color = Color.Gray,
                    style = style2.copy(textAlign = TextAlign.End),
                    text = chatMsg.lastMsgTimeStamp ?: "NA"
                )
            }
        }
    }
}


@Preview
@Composable
fun MessageInboxScreenPreview() {
//    MessagesSearchBox(navController)
}