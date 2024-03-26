package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Brush
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.KEYBOARD_CLOSE_ON_BACK_PRESS_KEY_CODE
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsData
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.OnPrimaryFixed
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary400
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary500
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily

@Composable
fun CommunityFullPostScreen(
    navController: NavController,
    currPostDetailViewModel: CurrPostDetailViewModel = hiltViewModel(),
    postSharedViewModel: PostSharedViewModel = hiltViewModel()
) {
    val allCommentsResponseList by currPostDetailViewModel.allCommentsStateFlow.collectAsStateWithLifecycle()
    val allCommentsData = allCommentsResponseList.allComments

    val currentPost by postSharedViewModel.postContentState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CommentsTopBarComposable(navController)
        },
        bottomBar = {
            AnimatedVisibility(true) {
                CommentsBottomBarComposable {}
            }
        },
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .95f)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(MaterialTheme.spacing.extraSmall)
        ) {
            PostComposableContent(
                post = currentPost ?: CommunityPost(),
                allCommentsData = allCommentsData
            )
        }
    }
}

@Composable
fun CommentsTopBarComposable(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Primary400),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.avgExtraSmall),
        shape = RoundedCornerShape(MaterialTheme.spacing.default)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.avgSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .clickable {
                        navController.navigate(CommunityAppScreens.CommunityScreen.route) {
                            popUpTo(CommunityAppScreens.CommunityFullPostScreen.route) {
                                inclusive = true
                            }
                        }
                    },
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

@Composable
fun PostComposableContent(
    post: CommunityPost,
    allCommentsData: List<AllCommentsData?>?
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val cardBorderBrush = Brush.linearGradient(colors = listOf(OnPrimaryFixed, Color.Transparent, OnPrimaryFixed, Color.Transparent))
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.extraSmall)
            .border(1.dp, cardBorderBrush, RoundedCornerShape(MaterialTheme.size.small)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)),
        shape = RoundedCornerShape(MaterialTheme.size.small)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 4.dp
            )
        ) {
            CommunityFullPostUpperRow(context, post)
            CommunityFullPostContent(post)
            CommunityFullPostLowerRow(post)
            ComposableCommunityPostLastRow(context, showCommentsAgain = false) {} // composable re-used from Community Screen
            HorizontalDivider()
            Row(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medSmall)) {
                Text(text = "Comments", style = style)
            }
            ComposableAllComments(allCommentsData)
        }
    }
}

@Composable
fun CommunityFullPostUpperRow(context: Context, post: CommunityPost) {
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
        modifier = Modifier.padding(MaterialTheme.spacing.lessSmall)
    ) {
        Image(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(MaterialTheme.size.large3))
                .size(MaterialTheme.size.large3)
                .clip(RoundedCornerShape(MaterialTheme.size.large3)),
            painter = painterResource(id = R.drawable.doctor_profile),
            contentDescription = "doctor avatar"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = MaterialTheme.spacing.avgSmall,
                    end = MaterialTheme.spacing.lessSmall
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                text = post.posterName ?: "--",
                style = style1
            )

            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
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
                    .padding(end = MaterialTheme.spacing.lessSmall)
                    .size(MaterialTheme.size.smallIconSize),
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
fun CommunityFullPostContent(post: CommunityPost) {
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
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.spacing.avgSmall,
                horizontal = MaterialTheme.spacing.extraSmall
            )
            .clip(RoundedCornerShape(MaterialTheme.spacing.lessSmall)),
        colors = CardDefaults.cardColors(containerColor = Primary500.copy(alpha = .1f)),
        shape = RoundedCornerShape(MaterialTheme.spacing.lessSmall)
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.avgSmall)) {
            Text(
                text = post.postTitle ?: "--",
                style = style1
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                text = post.postContent ?: "Nothing to see here .... ",
                style = style2
            )
        }
    }
}

@Composable
fun CommunityFullPostLowerRow(post: CommunityPost) {
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
    Row(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.medSmall, horizontal = MaterialTheme.spacing.lessSmall),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = "$reactionsCount Reactions", style = style)

        Spacer(modifier = Modifier.weight(1f))

        commentsCount?.let {
            Text(text = "$commentsCount Comments", style = style)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentsBottomBarComposable(
    onBottomBarStatusChanged: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    var userComment by remember { mutableStateOf("") }
    val painter = painterResource(id = R.drawable.doctor_profile)
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(true) }
    var topPadding by remember { mutableStateOf(0.dp) }
    var verticalAlignment by remember { mutableStateOf(Alignment.CenterVertically) }
    val style1 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W500,
        color = FocusedTextColor.copy(alpha = .5f),
        textAlign = TextAlign.Start
    )
    val style2 = TextStyle(
        textAlign = TextAlign.End,
        color = FocusedTextColor,
        fontWeight = FontWeight.W600
    )

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
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            color = MaterialTheme.colorScheme.surface.copy(alpha = .08f)
        )
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
                    .imePadding()
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
                    Text(text = "Leave your thoughts here ...", style = style1)
                },
                textStyle = style1.copy(color = FocusedTextColor),
                maxLines = 5,
            )
        }

        if (isFocused) {
            topPadding = 10.dp
            verticalAlignment = Alignment.Top
            HorizontalDivider()
            Text(
                text = "Post",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.avgSmall,
                        bottom = MaterialTheme.spacing.avgSmall,
                        end = MaterialTheme.spacing.medSmall,
                    )
                    .clickable {
                        MedCommToast.showToast(context, "Commenting...")
                    },
                style = style2
            )
        }
    }
}

@Composable
fun ComposableAllComments(allCommentsData: List<AllCommentsData?>?) {
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = workSansFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(allCommentsData ?: emptyList()) { allCommentsResult ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.extraSmall,
                        vertical = MaterialTheme.spacing.small
                    ),
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    modifier = Modifier.size(MaterialTheme.size.defaultIconSize),
                    painter = painterResource(id = R.drawable.doctor_profile),
                    contentDescription = "profile icon",
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = Primary500.copy(alpha = .1f)),
                    modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
                    shape = RoundedCornerShape(MaterialTheme.spacing.small)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.avgLessSmall),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.spacing.smallest),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = allCommentsResult?.commentedByUserName ?: "--",
                                style = style.copy(fontWeight = FontWeight.W600)
                            )
                            Icon(
                                modifier = Modifier.size(MaterialTheme.size.defaultIconSize),
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                        }

                        Row(
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = allCommentsResult?.commentedByUserId ?: "--",
                                style = style
                            )
                        }

                        Row(
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = allCommentsResult?.commentContent ?: "--",
                                style = style.copy(fontWeight = FontWeight.W500)
                            )
                        }

                        Row(
                            modifier = Modifier.padding(bottom = 2.dp)
                        ) {
                            Text(
                                text = (allCommentsResult?.replies?.size ?: "").toString(),
                                style = style
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CommentsScreenPreview() {}