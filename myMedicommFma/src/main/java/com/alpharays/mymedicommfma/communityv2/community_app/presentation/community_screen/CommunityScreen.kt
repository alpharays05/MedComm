package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen


import android.content.Context
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_COMMENT_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_REACT_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_REPOST_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_SEND_NO_CONNECTION
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
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.custom_top_appbar.CustomTopAppBar
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.sidedrawer.ModalNavigationDrawerScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BluishGray
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.OnSurfaceHighEmphasis
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary100
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary500
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommunityScreen(
    navController: NavController,
    communityViewModel: CommunityViewModel = hiltViewModel(),
    postSharedViewModel: PostSharedViewModel = hiltViewModel(),
) {
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isInternetAvailable by communityViewModel.networkStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val refreshing by communityViewModel.refreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            if (isInternetAvailable != ConnectivityObserver.Status.Available) {
                MedCommToast.showToast(context, NO_CONNECTION)
            }
            communityViewModel.refreshCommunityPosts()
        }
    )

    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState),
                topBar = {
                    CustomTopAppBar(drawerState = drawerState, showLogo = true, navController = navController)
                },
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .95f)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    ComposableCommunityPosts(
                        navController = navController,
                        isInternetAvailable = isInternetAvailable,
                        communityViewModel = communityViewModel,
                        viewModel = postSharedViewModel
                    )
                    PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
                }
            }
        },
        drawerContent = {
            ModalNavigationDrawerScreen(
                isLoggedIn = false,
                navController = navController,
                onSign = { },
                onSignOut = { }
            )
        }
    )
}

@Composable
fun ComposableCommunityPosts(
    navController: NavController,
    isInternetAvailable: ConnectivityObserver.Status,
    communityViewModel: CommunityViewModel,
    viewModel: PostSharedViewModel,
) {
    val context = LocalContext.current
    val allCommunityPostsResponse by communityViewModel.allCommunityPostsStateFlow.collectAsStateWithLifecycle()
    val communityPostsList = allCommunityPostsResponse.allPosts?.communityPostData
    var showAddNewAppointmentIcon by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    var errorFound by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current
    val cardBorderBrush = Brush.sweepGradient(colors = listOf(BluishGray, Color.Transparent, BluishGray, Color.Transparent))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = MaterialTheme.spacing.avgSmall,
                horizontal = MaterialTheme.spacing.extraSmall
            ),
        contentAlignment = Alignment.Center
    ) {
        communityPostsList?.let {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(communityPostsList) { index, post ->
                    Column(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialTheme.spacing.extraSmall)
                                .border(1.dp, cardBorderBrush, RoundedCornerShape(MaterialTheme.size.small)),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)),
                            shape = RoundedCornerShape(MaterialTheme.size.small)
                        ) {
                            Column(
                                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ComposableCommunityPostUpperRow(context, post)

                                ComposableCommunityPostContent(
                                    context = context,
                                    post = post,
                                    viewModel = viewModel,
                                    navController = navController,
                                    isInternetAvailable = isInternetAvailable
                                )

                                ComposableCommunityPostLowerRow(context, post)

                                ComposableCommunityPostLastRow(context, isInternetAvailable) {
                                    showAddNewAppointmentIcon = !it
                                }
                            }
                        }

                        AnimatedVisibility(visible = index != communityPostsList.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(horizontal = MaterialTheme.spacing.extraSmall)
                                    .clip(RoundedCornerShape(MaterialTheme.spacing.avgExtraSmall)),
                                thickness = MaterialTheme.spacing.avgExtraSmall,
                                color = OnSurfaceHighEmphasis.copy(alpha = .2f)
                            )
                        }
                    }
                }
            }
        }

        val isVisible = isInternetAvailable == ConnectivityObserver.Status.Available
        if(communityPostsList != null && communityPostsList.isEmpty() && isVisible){
            LaunchedEffect(Unit){
                MedCommToast.showToast(context, "No posts found")
            }
        }

        AnimatedVisibility(visible = isInternetAvailable == ConnectivityObserver.Status.Unavailable) {
            ComposableNoNetworkFound(
                context = context,
                networkStatus = isInternetAvailable,
                modifier = Modifier.padding(top = 15.dp),
                viewModel = communityViewModel
            )
        }

        AnimatedVisibility(visible = isInternetAvailable == ConnectivityObserver.Status.Lost) {
            LaunchedEffect(Unit) {
                MedCommToast.showToast(context, "No Connection")
            }
        }

        AnimatedVisibility(
            visible = showAddNewAppointmentIcon,
            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.size.large3)
                    .background(Primary100.copy(alpha = .5f), RoundedCornerShape(MaterialTheme.spacing.medSmall))
                    .clip(RoundedCornerShape(MaterialTheme.spacing.medSmall))
                    .border(0.5.dp, Color.Black, RoundedCornerShape(MaterialTheme.spacing.medSmall))
                    .clickable {
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
    val style1 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val style2 = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        textAlign = TextAlign.Start
    )
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
                text = post.posterName ?: "--",
                style = style1
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = post.aboutDoc ?: "--",
                maxLines = 1,
                style = style2
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
                tint = Color.Blue.copy(alpha = .8f)
            )
            Text(
                text = "Connect",
                style = style1
            )
        }
    }
}

@Composable
fun ComposableCommunityPostContent(
    context: Context,
    post: CommunityPost,
    viewModel: PostSharedViewModel,
    navController: NavController? = null,
    isInternetAvailable: ConnectivityObserver.Status = ConnectivityObserver.Status.Available,
) {
    val currentPostId = post.id
    val color = CommunityUtils.getMedicoColor(context, R.color.bluish_gray)
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    val ripple = rememberRipple(bounded = false, radius = 0.dp, color = Color(color))
    val style1 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val style2 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W300,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(MaterialTheme.spacing.lessSmall))
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) {
                if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                    viewModel.clearPostContentState()
                    viewModel.setOrUpdate(post)
                    navController?.navigate(CommunityAppScreens.CommunityPostScreen.route + "/$currentPostId")
                } else {
                    MedCommToast.showToast(context, NO_CONNECTION)
                }
            },
        colors = CardDefaults.cardColors(containerColor = Primary500.copy(alpha = .1f)),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = post.postTitle ?: "--",
                style = style1
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = post.postContent ?: "--", // TODO: take upto 15-20 characters of post content, rest show on "Read More" clickable annotated string
                style = style2
            )
        }
    }
}

@Composable
fun ComposableCommunityPostLowerRow(context: Context, post: CommunityPost) {
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
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val interactionSource = remember { MutableInteractionSource() }
    val indication = LocalIndication.current

    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = "$reactionsCount Reactions", style = style)

        Spacer(modifier = Modifier.weight(1f))

        commentsCount?.let {
            Text(text = "$commentsCount Comments", style = style)
        }
    }
}

@Composable
fun ComposableCommunityPostLastRow(
    context: Context,
    isInternetAvailable: ConnectivityObserver.Status = ConnectivityObserver.Status.Available,
    showCommentsAgain: Boolean = true,
    onBottomBarStatusChange: (Boolean) -> Unit,
) {
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
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = workSansFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    Column(
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(start = MaterialTheme.spacing.small, end = MaterialTheme.spacing.small, bottom = MaterialTheme.spacing.extraSmall),
            color = MaterialTheme.colorScheme.surface.copy(alpha = .08f)
        )
        Row(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.extraSmall)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommunityPostReactionOptions(
                style = style.copy(fontWeight = if(postLiked) FontWeight.Bold else style.fontWeight),
                context = context,
                painter = likePainter,
                contentDescription = LIKE_PAINTER_CONTENT_DSC,
                imageText = LIKE_OPTION,
                onSingleTap = { offSet ->
                    if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                        postLiked = !postLiked
                    } else {
                        MedCommToast.showToast(context, CAN_NOT_REACT_NO_CONNECTION)
                    }
                },
                onLongPress = { offSet ->
                    isReactionsVisible = true
                    pressOffset = IntOffset(offSet.x.toInt(), offSet.y.toInt())
                }
            )
            CommunityPostReactionOptions(
                context = context,
                painter = commentPainter,
                contentDescription = COMMENT_PAINTER_CONTENT_DSC,
                imageText = COMMENT_OPTION,
                style = style,
                onSingleTap = {
                    if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                        if (showCommentsAgain) {
                            isCommentsVisible = !isCommentsVisible
                        }
                    } else {
                        MedCommToast.showToast(context, CAN_NOT_COMMENT_NO_CONNECTION)
                    }
                }
            )
            CommunityPostReactionOptions(
                context = context,
                painter = repostPainter,
                contentDescription = REPOST_PAINTER_CONTENT_DSC,
                imageText = REPOST_OPTION,
                style = style,
                onSingleTap = {
                    if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                        // TODO:
                    } else {
                        MedCommToast.showToast(context, CAN_NOT_REPOST_NO_CONNECTION)
                    }
                }
            )
            CommunityPostReactionOptions(
                context = context,
                painter = sendPainter,
                contentDescription = SEND_PAINTER_CONTENT_DSC,
                imageText = SEND_OPTION,
                style = style,
                onSingleTap = {
                    if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                        // TODO:
                    } else {
                        MedCommToast.showToast(context, CAN_NOT_SEND_NO_CONNECTION)
                    }
                }
            )
        }
        AnimatedVisibility(visible = isCommentsVisible && showCommentsAgain) {
            HorizontalDivider(
                modifier = Modifier.padding(start = MaterialTheme.spacing.small, end = MaterialTheme.spacing.small, bottom = MaterialTheme.spacing.extraSmall),
                color = MaterialTheme.colorScheme.surface.copy(alpha = .08f)
            )
            CommentsBottomBarComposable {
                onBottomBarStatusChange(it)
            }
        }
    }

    AnimatedVisibility(visible = isReactionsVisible) {
        ComposablePopReactionsRow(context, pressOffset, itemHeight, density) {
            isReactionsVisible = false
        }
    }
}

@Composable
fun CommunityPostReactionOptions(
    context: Context,
    style: TextStyle,
    painter: Painter,
    contentDescription: String,
    imageText: String,
    onSingleTap: (Offset) -> Unit = {},
    onLongPress: (Offset) -> Unit = {},
) {
    Column(
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
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.extraSmall)
                .size(MaterialTheme.size.extraSmallIconSize),
            painter = painter,
            contentDescription = contentDescription
        )
        Text(
            text = imageText,
            style = style
        )
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
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

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
                    Text(text = "Leave your thoughts here...", style = style)
                },
                textStyle = style.copy(color = FocusedTextColor),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
        }
    }
}

enum class ReactionPainters(private val drawableId: Int) {
    Like(R.drawable.like_reaction),
    Love(R.drawable.love_reaction),
    Celebrate(R.drawable.celebrate_reaction),
    Support(R.drawable.support_reaction),
    Insightful(R.drawable.insightful_reaction),
    Funny(R.drawable.funny_reaction);

    fun getReactionPainterId(): Int {
        return drawableId
    }
}

@Composable
fun ComposablePopReactionsRow(
    context: Context,
    pressOffset: IntOffset,
    itemHeight: Dp,
    density: Density,
    onDismiss: () -> Unit,
) {
    val reactionPainters = ReactionPainters.entries

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
                    painterId = painterId.getReactionPainterId(),
                    index = index,
                    context = context
                )
            }
        }
    }
}

@Composable
fun AnimatedVisibilityWithDelay(painterId: Int, index: Int, context: Context) {
    // TODO: animated visibility with compose specific events - tweeen, expandH or expandV
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