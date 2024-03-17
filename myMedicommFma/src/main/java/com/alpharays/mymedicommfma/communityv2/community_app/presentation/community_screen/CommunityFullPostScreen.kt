package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

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
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.OnPrimaryFixed
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary500
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily

@Composable
fun CommunityFullPostScreen(
    navController: NavController,
    postSharedViewModel: PostSharedViewModel = hiltViewModel(),
    currPostDetailViewModel: CurrPostDetailViewModel = hiltViewModel(),
) {
    val allCommentsResponseList by currPostDetailViewModel.allCommentsStateFlow.collectAsStateWithLifecycle()
    val allCommentsData = allCommentsResponseList.allComments
    val currentPost by postSharedViewModel.postContentState.collectAsStateWithLifecycle()
    println("allCommentsResponseList :: $allCommentsResponseList")

    Scaffold(
        topBar = {
            CommentsTopBarComposable()
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
                .padding(4.dp)
        ) {
            PostComposableContent(
                post = currentPost ?: CommunityPost(),
                allCommentsData = allCommentsData,
                viewModel = postSharedViewModel
            )
        }
    }
}

@Composable
fun CommentsTopBarComposable() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(0.dp)
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

@Composable
fun PostComposableContent(
    post: CommunityPost,
    allCommentsData: List<AllCommentsData?>?,
    viewModel: PostSharedViewModel,
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
            ComposableCommunityPostUpperRow(context, post)
            ComposableCommunityPostContent(context, post, viewModel)
            ComposableCommunityPostLowerRow(context, post)
            ComposableCommunityPostLastRow(context, showCommentsAgain = false) {}
            HorizontalDivider()
            Row(
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Text(text = "Comments", style = style)
            }
            ComposableAllComments(allCommentsData)
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
                    .padding(horizontal = MaterialTheme.spacing.extraSmall, vertical = MaterialTheme.spacing.small),
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
                        modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.avgLessSmall),
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
fun CommentsScreenPreview() {
}