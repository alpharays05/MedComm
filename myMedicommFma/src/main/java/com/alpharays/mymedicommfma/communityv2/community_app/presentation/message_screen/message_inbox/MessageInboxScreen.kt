package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alpharays.medico.R
import com.alpharays.medico.medico_utils.MedicoUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.ChatMsg
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
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

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            ComposableMessageInboxSearch(
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

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(top = 2.dp),
    ) {
        SearchBar(
            modifier = Modifier.weight(0.85f).padding(5.dp),
            shape = RoundedCornerShape(10.dp),
            query = messageSearchText,
            onQueryChange = { query ->
                messageSearchText = query
                if (query.lowercase().isEmpty()) {
                    messageSearchItems.clear()
                    messageSearchItems.addAll(messageSearchHistoryItems)
                } else {
                    val filteredItems = searchList?.filter { chatMsg ->
                        chatMsg.lastMessage?.lowercase()?.contains(query.lowercase()) == true
                    }.orEmpty()
                    messageSearchItems.clear()
                    messageSearchItems.addAll(filteredItems)
                }
            },
            onSearch = {
                searchedChatMsg?.let { chatMsg ->
                    messageSearchHistoryItems.add(chatMsg)
                }
                searchBarActive = false
                onNewSearchedList(messageSearchItems.toTypedArray())
            },
            active = searchBarActive,
            onActiveChange = {
                searchBarActive = it
            },
            placeholder = {
                Text(text = "Search messages")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        if (messageSearchText.isNotEmpty()) messageSearchText = ""
                        else {
                            searchBarActive = false
                        }
                        onReset()
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        ) {
            LazyColumn{
                items(messageSearchItems){
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                searchedChatMsg = it
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.padding(10.dp),
                            imageVector = Icons.Default.History,
                            contentDescription = "History Icon"
                        )
                        Text(text = it.lastMessage.toString())
                    }
                }
            }
        }
    }
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

    if(!newSearchedList.isNullOrEmpty()){
        list = newSearchedList
    }

    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(list) {
            ComposableMessageBoxCard(it, navController)
        }
    }
}


@Composable
fun ComposableMessageBoxCard(chatMsg: ChatMsg, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(0.5.dp, Color.White, RoundedCornerShape(5.dp))
            .clickable {
                navController.navigate(CommunityAppScreens.DirectMessageScreen.route)
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(5.dp)
        ) {
            val context = LocalContext.current
            val painter = painterResource(id = R.drawable.doctor_profile)
            val color = MedicoUtils.getMedicoColor(context, R.color.bluish_gray)
            Image(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(45.dp)
                    .border(1.dp, Color(color), RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp)),
                painter = painter,
                contentDescription = "User avatar"
            )
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp)
            ) {
                Text(
                    text = "Dr Shivang Gautam",
                    style = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Monospace
                    )
                )
                Text(
                    text = chatMsg.lastMessage ?: "NA",
                    style = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp
                    )
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    color = Color.Gray,
                    style = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End
                    ),
                    text = chatMsg.lastMsgTimeStamp ?: "NA"
                )
            }
        }
    }
}


@Preview
@Composable
fun MessageInboxScreenPreview() {

}