package com.alpharays.mymedicommfma.community_app.presentation.community_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alpharays.mymedicommfma.MedicoApp
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.allposts.CommunityPost
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsData
import com.alpharays.mymedicommfma.community_app.community_utils.getCommunityViewModel
import com.alpharays.mymedicommfma.presentation.theme.size
import com.alpharays.mymedicommfma.medico_utils.MedicommConstants.KEYBOARD_CLOSE_ON_BACK_PRESS_KEY_CODE
import com.alpharays.mymedicommfma.medico_utils.connectivity.ConnectivityObserver

@Composable
fun CommunityFullPostScreen(
    navController: NavController,
    isInternetAvailable: ConnectivityObserver.Status,
    postCommentsSharedViewModel: PostCommentsSharedViewModel,
) {
    val communityUseCase = MedicoApp
        .getInstance()
        .getCommunityInjector()
        .getCommunityUseCase()
    val currPostCommentsViewModel: CurrPostCommentsViewModel = getCommunityViewModel(communityUseCase)

    val allCommentsResponseList by currPostCommentsViewModel.allCommentsStateFlow.collectAsStateWithLifecycle()

    var allCommentsData by remember {
        mutableStateOf<List<AllCommentsData?>?>(null)
    }

    allCommentsResponseList.allComments?.let {
        allCommentsData = it
    }

    println("allCommentsResponseList :: $allCommentsResponseList")

    val postContentState by postCommentsSharedViewModel.postContentState.collectAsStateWithLifecycle()
    var post by remember {
        mutableStateOf(CommunityPost())
    }

    postContentState?.let {
        post = it
    }

    Scaffold(
        topBar = {
            CommentsTopBarComposable()
        },
        bottomBar = {
            AnimatedVisibility(true) {
                CommentsBottomBarComposable {}
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(4.dp)
        ) {
            PostComposableContent(post, allCommentsData, postCommentsSharedViewModel)
        }
    }
}


@Composable
fun PostComposableContent(
    post: CommunityPost,
    allCommentsData: List<AllCommentsData?>?,
    viewModel: PostCommentsSharedViewModel,
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .border(0.9.dp, Color(0xFF3B4557), RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 4.dp
            )
        ) {
            ComposableCommunityPostUpperRow(context, post)
            ComposableCommunityPostContent(context, post, viewModel)
            ComposableCommunityPostLowerRow(context, post)
            ComposableCommunityPostLastRow(context, false) {}
            Divider()
            Row(
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Text(text = "Comments")
            }
            ComposableAllComments(allCommentsData)
        }
    }
}


@Composable
fun CommentsTopBarComposable() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "close post"
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more options"
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentsBottomBarComposable(
    onBottomBarStatusChanged: (Boolean) -> Unit,
) {
    var userComment by remember { mutableStateOf("") }
    val painter = painterResource(id = R.drawable.doctor_profile)
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(true) }
    var topPadding by remember { mutableStateOf(0.dp) }
    var verticalAlignment by remember {
        mutableStateOf(Alignment.CenterVertically)
    }

    LaunchedEffect(isFocused) {
        onBottomBarStatusChanged(isFocused)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        BackHandler(enabled = isFocused) {
            focusManager.clearFocus()
            isFocused = false
        }
        Divider()
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = verticalAlignment
        ) {
            Image(
                modifier = Modifier
                    .padding(top = topPadding)
                    .size(35.dp)
                    .border(0.5.dp, Color.Gray, RoundedCornerShape(50.dp)),
                painter = painter,
                contentDescription = "CD"
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .onPreInterceptKeyBeforeSoftKeyboard {
                        if (it.key.keyCode == KEYBOARD_CLOSE_ON_BACK_PRESS_KEY_CODE) {
                            focusManager.clearFocus()
                        }
                        true
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                value = userComment,
                onValueChange = { newText ->
                    userComment = newText
                },
                placeholder = {
                    Text(text = "Leave your thoughts here...")
                },
                textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF00897B)),
                maxLines = 5,
            )
        }

        if (isFocused) {
            topPadding = 10.dp
            verticalAlignment = Alignment.Top
            Divider()
            Text(
                text = "Post",
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                textAlign = TextAlign.End
            )
        }
    }
}


@Composable
fun ComposableAllComments(allCommentsData: List<AllCommentsData?>?) {
    allCommentsData?.let { allCommentsResponse ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(6.dp),
        ) {
            items(allCommentsResponse) { allCommentsResult ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        modifier = Modifier.size(MaterialTheme.size.defaultIconSize),
                        painter = painterResource(id = R.drawable.doctor_profile),
                        contentDescription = "profile icon",
                    )

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFCBD6DF)),
                        modifier = Modifier.padding(start = 4.dp),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = allCommentsResult?.commentedByUserName ?: "User name",
                                    fontSize = 14.sp,
                                )
                                Icon(
                                    modifier = Modifier.size(MaterialTheme.size.lessMedium),
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "",
                                    tint = Color.Unspecified
                                )
                            }

                            Row(
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Text(
                                    text = allCommentsResult?.commentedByUserId
                                        ?: "About commented by id",
                                    fontSize = 10.sp,
                                    color = Color.Black.copy(alpha = 0.7f)
                                )
                            }

                            Row(
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = allCommentsResult?.commentContent ?: "comment content",
                                    fontSize = 10.sp,
                                )
                            }

                            Row(
                                modifier = Modifier.padding(bottom = 2.dp)
                            ) {
                                Text(
                                    text = (allCommentsResult?.replies?.size
                                        ?: "0 replies").toString(),
                                    fontSize = 10.sp,
                                    color = Color.Black.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CommentsScreenPreview() {
    val a = AllCommentsData("1", "2", "3", "4", "5")
    val b = AllCommentsData("1", "2", "3", "4", "5")
    val c = AllCommentsData("1", "2", "3", "4", "5")
    val allCommentsData = listOf(a, b, c)
    ComposableAllComments(allCommentsData)
}