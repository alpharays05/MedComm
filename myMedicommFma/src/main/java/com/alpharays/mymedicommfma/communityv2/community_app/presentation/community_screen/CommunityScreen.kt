package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen


import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENT_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENT_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LIKE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LIKE_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.REPOST_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.REPOST_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SEND_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SEND_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils.Companion.ComposableNoNetworkFound
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.getCommunityViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CommunityScreen(
    navController: NavController,
    isInternetAvailable: ConnectivityObserver.Status,
    modifier: Modifier,
) {
    val communityScreenUseCase = MedCommRouter
        .getCommunityInjector()
        .getCommunityUseCase()
    val communityViewModel: CommunityViewModel = getCommunityViewModel(communityScreenUseCase)
    val postCommentsSharedViewModel: PostCommentsSharedViewModel =  getCommunityViewModel(null)
    val context = LocalContext.current

    LaunchedEffect(isInternetAvailable) {
        val isNetworkAlreadyLost = false //todo
        communityViewModel.updateNetworkStatus(isInternetAvailable)
        if (isInternetAvailable == ConnectivityObserver.Status.Lost && !isNetworkAlreadyLost) {
            MedCommToast.showToast(context, "Connection Lost")
        }
    }

    var showBottomBar by remember { mutableStateOf(true) }

    Surface(modifier = modifier.fillMaxSize()) {
       Scaffold { innerPadding ->
            ComposableCommunityScreen(
                context,
                navController,
                isInternetAvailable,
                Modifier,
                innerPadding,
                postCommentsSharedViewModel,
                communityViewModel
            ){ updatedStatus ->
                showBottomBar = !updatedStatus
            }
        }
    }
}


@Composable
fun ComposableCommunityScreen(
    context: Context,
    navController: NavController,
    isInternetAvailable: ConnectivityObserver.Status,
    modifier: Modifier,
    innerPadding: PaddingValues,
    viewModel: PostCommentsSharedViewModel?,
    communityViewModel: CommunityViewModel,
    onBottomStatus : (Boolean) -> Unit
) {
    Column(
        modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Community",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 10.dp),
        )

        ComposableCommunitySearch(context, navController)

        ComposableCommunityPosts(context, navController, isInternetAvailable, communityViewModel, viewModel){
            onBottomStatus(it)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableCommunitySearch(
    context: Context,
    navController: NavController,
) {
    var communitySearchText by remember {
        mutableStateOf("")
    }
    var searchBarActive by remember {
        mutableStateOf(false)
    }
    // history items will be stored in ROOM DB, no need to store remotely
    val communityHistoryItems = remember {
        mutableStateListOf<String>()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "twinkling_icon")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 650,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "twinkling_icon"
    )

    val textColor = Color(0xFF00897B)

    Row(
        verticalAlignment = Alignment.Top,
    ) {
        SearchBar(
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            shape = RoundedCornerShape(6.dp),
            colors = SearchBarDefaults.colors(
                containerColor = Color(0xFFE9EDF5),
                inputFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
//                    disabledTextColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent
                )
            ),
            query = communitySearchText,
            onQueryChange = {
                communitySearchText = it
            },
            onSearch = {
                communityHistoryItems.add(communitySearchText)
                searchBarActive = false
            },
            active = searchBarActive,
            onActiveChange = {
                searchBarActive = it
            },
            placeholder = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (!searchBarActive) {
                        Divider(
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                                .alpha(alpha),
                            color = Color(0xFF9CBDFF)
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "What are you looking for ?"
                    )
                }
            },
            trailingIcon = {
                if (searchBarActive) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (communitySearchText.isNotEmpty()) communitySearchText = ""
                            else {
                                searchBarActive = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            }
        ) {
            communityHistoryItems.forEach {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = "History Icon"
                    )
                    Text(text = it)
                }
            }
        }

        Image(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp, 25.dp, 10.dp, 10.dp)
                .clickable {
                    MedCommToast.showToast(context, "Going to inbox...")
                    navController.navigate(CommunityAppScreens.MessageInboxScreen.route) {
                        launchSingleTop = true
                    }
                },
            painter = painterResource(id = R.drawable.messages),
            contentDescription = "Message icon"
        )
    }
}


@Composable
fun ComposableCommunityPosts(
    context: Context,
    navController: NavController,
    isInternetAvailable: ConnectivityObserver.Status,
    communityViewModel: CommunityViewModel,
    viewModel: PostCommentsSharedViewModel?,
    onBottomBarStatusChange : (Boolean) -> Unit
) {
    val allCommunityPostsResponse by communityViewModel.allCommunityPostsStateFlow.collectAsStateWithLifecycle()
    var showAddNewAppointmentIcon by remember {
        mutableStateOf(true)
    }

    val communityPostState = remember {
        mutableStateListOf<List<CommunityPost>>()
    }
    var communityPostsList by remember {
        mutableStateOf<List<CommunityPost>?>(null)
    }
    val scope = rememberCoroutineScope()
    var errorFound by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(allCommunityPostsResponse) {
        with(allCommunityPostsResponse) {
            if (isLoading != null && isLoading == true) {
                MedCommToast.showToast(context, "Loading posts")
                return@LaunchedEffect
            }

            if (!error.isNullOrEmpty()) {
                errorFound = true
                MedCommToast.showToast(context, SOMETHING_WENT_WRONG)
                return@LaunchedEffect
            }

            allPosts?.communityPostData?.let { posts ->
                communityPostsList = posts
            }
        }
    }

    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Box(modifier = Modifier.fillMaxSize()) {
        communityPostsList?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 5.dp)
            ) {
                itemsIndexed(it) { index, post ->
                    Card(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .border(0.9.dp, Color(0xFF3B4557), RoundedCornerShape(6.dp))
                            .clip(RoundedCornerShape(6.dp)),
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

                            ComposableCommunityPostContent(context, post, viewModel, navController)

                            ComposableCommunityPostLowerRow(context, post)

                            ComposableCommunityPostLastRow(context){
                                onBottomBarStatusChange(it)
                                showAddNewAppointmentIcon = !it
                            }
                        }
                    }
                }
            }
        }

        if (isInternetAvailable == ConnectivityObserver.Status.Unavailable) {
            ComposableNoNetworkFound(context, Modifier.padding(top = 15.dp), communityViewModel)
        }
        if (isInternetAvailable == ConnectivityObserver.Status.Available) {
            if (communityPostsList.isNullOrEmpty() && !errorFound) {
                LaunchedEffect(Unit) {
                    scope.launch {
                        delay(10000L) // TODO : even though delay is 10s still it shows No posts found : IMP -> on log response is coming from the server : so UI issue
                        if (communityPostState.isEmpty()) {
//                            CustomToast.showToast(context, "No posts found") // TODO : errorFound is delayed and received later after this toast is displayed
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showAddNewAppointmentIcon,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 3.dp, 0.5.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFFF7FFFE), RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .border(0.5.dp, Color.Black, RoundedCornerShape(12.dp))
                    .clickable {
                        MedCommToast.showToast(context, "Adding new post")
                        navController.navigate(CommunityAppScreens.AddNewCommunityPostScreen.route) {
                            launchSingleTop = true
                        }
                    },
                imageVector = Icons.Default.Add,
                contentDescription = "Add new community post",
                tint = Color.Blue
            )
        }
    }
}


@Composable
fun ComposableCommunityPostUpperRow(context: Context, post: CommunityPost) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        Image(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(40.dp))
                .size(45.dp)
                .clip(RoundedCornerShape(40.dp)),
            painter = painterResource(id = R.drawable.doctor_profile),
            contentDescription = "doctor avatar"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = post.posterName ?: "NA",
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = post.aboutDoc ?: "About not set",
                maxLines = 1,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    letterSpacing = TextUnit(0.1f, TextUnitType.Sp)
                )
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.clickable {
                MedCommToast.showToast(context, "Connecting")
            }
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(20.dp),
                imageVector = Icons.Default.PersonAdd,
                contentDescription = "connect",
                tint = Color.Blue
            )
            Text(text = "Connect", fontSize = 14.sp)
        }
    }
}


@Composable
fun ComposableCommunityPostContent(
    context: Context,
    post: CommunityPost,
    viewModel: PostCommentsSharedViewModel?,
    navController: NavController? = null,
) {
    val currentPostId = post.id
    val color = CommunityUtils.getMedicoColor(context, R.color.bluish_gray)

    val interactionSource by remember {
        mutableStateOf(MutableInteractionSource())
    }
    val ripple = rememberRipple(bounded = false, radius = 0.dp, color = Color(color))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) {
                viewModel?.clearPostContentState()
                viewModel?.setOrUpdate(post)
                navController?.navigate(CommunityAppScreens.CommunityPostScreen.route + "/$currentPostId")
//                CustomToast.showToast(context, "Clicked at $index")
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = post.postTitle ?: "NA",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = post.postContent ?: "NA",
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}


@Composable
fun ComposableCommunityPostLowerRow(
    context: Context,
    post: CommunityPost,
) {
    var reactionsCount = 0
    post.reactions?.let { reactions ->
        reactionsCount += reactions.like?.size ?: 0
        reactionsCount += reactions.love?.size ?: 0
        reactionsCount += reactions.celebrate?.size ?: 0
        reactionsCount += reactions.support?.size ?: 0
        reactionsCount += reactions.insightful?.size ?: 0
        reactionsCount += reactions.funny?.size ?: 0
    }
    val commentsCount = post.comments?.size
    val style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400)
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = "$reactionsCount Reactions", style = style)

        Spacer(modifier = Modifier.weight(1f))

        commentsCount?.let {
            Text(
                text = "$commentsCount Comments",
                style = style
            )
        }
    }
}


@Composable
fun ComposableCommunityPostLastRow(context: Context, showCommentsAgain: Boolean = true, onBottomBarStatusChange: (Boolean) -> Unit) {
    var postLiked by remember { mutableStateOf(false) }
    var likePainterId by remember { mutableIntStateOf(R.drawable.not_liked) }
    LaunchedEffect(postLiked) {
        likePainterId = if (postLiked) R.drawable.like_reaction else R.drawable.not_liked
    }
    val likePainter = painterResource(id = likePainterId)
    val commentPainter = painterResource(id = R.drawable.comment)
    val repostPainter = painterResource(id = R.drawable.repost)
    val sendPainter = painterResource(id = R.drawable.send)
    var pressOffset by remember { mutableStateOf(IntOffset.Zero) }
    var isReactionsVisible by remember { mutableStateOf(false) }
    var isCommentsVisible by remember { mutableStateOf(false) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val focusRequester = remember { FocusRequester() }
//    val interactionSource = remember { MutableInteractionSource() }
//    val indication = rememberRipple(color = Color.LightGray)

    Column(
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Divider()
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 2.dp)
                .fillMaxWidth(), //  TODO : .indication(interactionSource, indication) - NOT WORKING
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CommunityPostReactionImageUseCase(
                context,
                likePainter,
                LIKE_PAINTER_CONTENT_DSC,
                LIKE_OPTION,
                onSingleTap = { offSet ->
                    postLiked = !postLiked
//                    CustomToast.showToast(context, "Single tap")
                },
                onLongPress = { offSet ->
//                    CustomToast.showToast(context, "Long pressed")
                    isReactionsVisible = true
                    pressOffset = IntOffset(offSet.x.toInt(), offSet.y.toInt())
                }
            )
            CommunityPostReactionImageUseCase(
                context,
                commentPainter,
                COMMENT_PAINTER_CONTENT_DSC,
                COMMENT_OPTION,
                onSingleTap = {
                    if(showCommentsAgain){
                        isCommentsVisible = !isCommentsVisible
                    }
                }
            )
            CommunityPostReactionImageUseCase(
                context,
                repostPainter,
                REPOST_PAINTER_CONTENT_DSC,
                REPOST_OPTION
            )
            CommunityPostReactionImageUseCase(
                context,
                sendPainter,
                SEND_PAINTER_CONTENT_DSC,
                SEND_OPTION
            )
        }


        AnimatedVisibility(visible = isCommentsVisible && showCommentsAgain) {
            Divider(modifier = Modifier.padding(vertical = 2.dp))
            CommentsBottomBarComposable{
                onBottomBarStatusChange(it)
            }
        }
    }

    if (isReactionsVisible) {
        // show reactions Row
        ComposableReactionsRow(context, pressOffset, itemHeight, density) {
            isReactionsVisible = false
        }
    }
}


@Composable
fun ComposableExpandedComment(focusRequester: FocusRequester, onDismiss: () -> Unit) {
    var userComment by remember { mutableStateOf("") }
    val painter = painterResource(id = R.drawable.doctor_profile)
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    AnimatedVisibility(
        visible = true,
        enter = expandVertically(), // Animation for entering
        exit = shrinkVertically() // Animation for exiting
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(35.dp)
                    .border(0.5.dp, Color.Gray, RoundedCornerShape(50.dp)),
                painter = painter,
                contentDescription = "CD"
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
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
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
        }
    }
}


@Composable
fun ComposableReactionsRow(
    context: Context,
    pressOffset: IntOffset,
    itemHeight: Dp,
    density: Density,
    onDismiss: () -> Unit,
) {
    val reactionPainters = listOf(
        R.drawable.like_reaction,
        R.drawable.love_reaction,
        R.drawable.celebrate_reaction,
        R.drawable.support_reaction,
        R.drawable.insightful_reaction,
        R.drawable.funny_reaction
    )

    val color = Color.White
    val borderColor = Color(0xFFCECECE)
    val shape = RoundedCornerShape(8.dp)
    val marginAboveIcon = 40.dp
    val itemHeightPx = with(density) { itemHeight.roundToPx() }
    val marginAboveIconPx = with(density) { marginAboveIcon.roundToPx() }
    val yOffset = pressOffset.y + itemHeightPx + marginAboveIconPx

    Popup(
        onDismissRequest = onDismiss,
        offset = IntOffset(pressOffset.x, yOffset),
        properties = PopupProperties(
            focusable = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            excludeFromSystemGesture = true
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(color, shape)
                .border(1.dp, borderColor, shape)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            reactionPainters.forEachIndexed { index, painterId ->
                AnimatedVisibilityWithDelay(
                    painterId = painterId,
                    index = index,
                    context = context
                )
            }
        }
    }
}


@Composable
fun AnimatedVisibilityWithDelay(painterId: Int, index: Int, context: Context) {
    // Define animation specs for enter animation
    val enterAnimation = slideInHorizontally(initialOffsetX = { -20 }) + fadeIn()
    val exitAnimation = slideOutHorizontally(targetOffsetX = { -20 }) + fadeOut()

    // Use AnimatedVisibility for each image
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(index * 5L) // Staggered delay, adjust the multiplier as needed
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = enterAnimation,
        exit = exitAnimation
    ) {
        // Replace with your CommunityPostReactionImageUseCase composable
        Image(
            painter = painterResource(id = painterId),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
    }
}


@Composable
fun CommunityPostReactionImageUseCase(
    context: Context,
    painter: Painter,
    contentDescription: String,
    imageText: String,
    onSingleTap: (Offset) -> Unit = {},
    onLongPress: (Offset) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = { offset ->
                        onLongPress(offset)
                    },
                    onTap = { offset ->
                        onSingleTap(offset)
                    }
                )
            }
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painter,
            contentDescription = contentDescription
        )
        Text(
            text = imageText,
            fontSize = 14.sp
        )
    }
}


@Preview
@Composable
fun CommunityScreenPreview() {
    val context = LocalContext.current
    val density = LocalDensity.current
    val navController = rememberNavController()
    Surface(modifier = Modifier.fillMaxSize()) {
//        ComposableCommunitySearch(context, navController)
//        ComposableReactionsRow(context, IntOffset.Zero, 10.dp, density){}
//        ComposableCommunityPostLastRow(context)
//        ComposableCommunityPostLowerRow(context, AllCommunityPosts())
    }
}