package com.alpharays.mymedicommfma.community_app.presentation.community_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.common.basesdk.BaseSDK
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.community_app.community_utils.getCommunityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCommunityPostScreen(
    navController: NavController,
    isInternetAvailable: ConnectivityObserver.Status
) {
    val communityScreenUseCase = BaseSDK.getCommunityInjector().getCommunityUseCase()
    val communityViewModel: CommunityViewModel = getCommunityViewModel(communityScreenUseCase)
    var postTitle by remember { mutableStateOf("") }
    var postContent by remember { mutableStateOf("") }
    val color = Color(0xFFF5F6FF)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = color,
        topBar = {
            ComposableNewPostTopBar(
                navController,
                communityViewModel,
                postTitle,
                postContent,
                isInternetAvailable
            )
        },
        bottomBar = {
            ComposableNewPostBottomBar()
        }
    ) { paddingValues ->
        Surface(
            color = color,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    ComposablePostOutlinedTextField(true) {
                        postTitle = it
                    }
                }
                item {
                    ComposablePostOutlinedTextField(false) {
                        postContent = it
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposablePostOutlinedTextField(
    isTitle: Boolean,
    onPostTextField: (String) -> Unit
) {
    val textColor0 = Color(0xFF006372)
    val textColor = Color(0xFF003D46)
    val postHeadingLabel = if (isTitle) "Add a title" else "Add a description"
    var postInputField by remember { mutableStateOf("") }
    val color = MaterialTheme.colorScheme.onPrimary
    val style = if (isTitle) {
        TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            color = textColor
        )
    } else {
        TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = textColor
        )
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        value = postInputField,
        onValueChange = { newText ->
            postInputField = newText
            onPostTextField(postInputField)
        },
        label = {
            Text(text = postHeadingLabel, style = style)
        },
        textStyle = TextStyle(fontSize = 14.sp, color = color),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
        )
    )
}


@Composable
fun ComposableNewPostTopBar(
    navController: NavController,
    communityViewModel: CommunityViewModel,
    postTitle: String,
    postContent: String,
    isInternetAvailable: ConnectivityObserver.Status
) {
    val response by communityViewModel.addNewCommunityPostStateFlow.collectAsStateWithLifecycle()
    var postCreated by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val color = Color(0xFF71FFEC)
    val ripple = rememberRipple(bounded = false, radius = 24.dp, color = color)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(0.dp, 0.dp, 5.dp, 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .clickable {
                        navController.navigate(CommunityAppScreens.CommunityScreen.route) {
                            popUpTo(CommunityAppScreens.AddNewCommunityPostScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                imageVector = Icons.Default.Close,
                contentDescription = "close post"
            )
            Text(
                modifier = Modifier.padding(8.dp).weight(1f),
                text = "Create a post",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )

            Box(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple,
                    ) {
                        if (isInternetAvailable != ConnectivityObserver.Status.Available) {
                        //    MedicoToast.showToast(context, "No connection") todo
                            return@clickable
                        }
                        if (postTitle.isEmpty() || postContent.isEmpty()) {
                      //      MedicoToast.showToast(context, "Post title/content can not be empty") todo
                            return@clickable
                        }
                        if (postContent.length < 20) {
//                            MedicoToast.showToast( todo
//                                context,
//                                "Post Content must greater than 20 characters"
//                            )
                            return@clickable
                        }
                        val post = AddNewCommunityPost(postTitle, postContent)
                        communityViewModel.addNewCommunityPost(post)
//                        CustomToast.showToast(context, "${post.postContent} : ${post.postTitle}")
                    }
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Post",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                    color = Color.Blue
                )
            }
        }
    }

    LaunchedEffect(response) {
        with(response) {
            newPostResponse?.let {
                it.success?.toInt()?.let { isPostCreated ->
                    if (isPostCreated == 1 && !postCreated) {
                        postCreated = true
                  //      MedicoToast.showToast(context, "Post created successfully") todo
                        navController.navigate(CommunityAppScreens.CommunityScreen.route) {
                            popUpTo(CommunityAppScreens.AddNewCommunityPostScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ComposableNewPostBottomBar() {
    var isExpandedMoreOptions by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val color = Color(0xFF768BFF)
    val ripple = rememberRipple(bounded = false, radius = 24.dp, color = color)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp, 18.dp, 0.dp, 0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                val imageVectorA = Icons.Default.AddPhotoAlternate
                val cdA = "gallery"
                val iconNameA = "Add photo"
                ComposableNewPostMediaColumn(imageVectorA, cdA, iconNameA)


                val imageVectorB = Icons.Default.AddAPhoto
                val cdB = "take photo"
                val iconNameB = "Capture"
                ComposableNewPostMediaColumn(imageVectorB, cdB, iconNameB)

                if (isExpandedMoreOptions) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 5.dp)
                            .size(30.dp)
                            .clickable {
                                isExpandedMoreOptions = false
                            },
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "close more options"
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 5.dp)
                            .size(30.dp)
                            .clickable {
                                isExpandedMoreOptions = true
                            },
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = "open more options"
                    )
                }
            }

            AnimatedVisibility(visible = isExpandedMoreOptions) {
                Column {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(top = 5.dp)
                    )
                    ComposableNewPostExpandedOptions(
                        Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 10.dp
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun ComposableNewPostMediaColumn(imageVector: ImageVector, cd: String, text: String) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 5.dp)
            .clickable {
            //    MedicoToast.showToast(context, "Coming soon...") todo
            }
    ) {
        Icon(
            modifier = Modifier.padding(5.dp).size(20.dp),
            imageVector = imageVector,
            contentDescription = cd
        )
        Text(
            text = text,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}


@Composable
fun ComposableNewPostExpandedOptions(modifier: Modifier) {
    var checked by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 10.dp, end = 10.dp),
            text = "Comment",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W600)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 10.dp, end = 14.dp)
                    .weight(1f),
                text = "Enabling comment will allow others to comment on your post",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )

            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            tint = Color.Blue
                        )
                    }
                } else {
                    null
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.LightGray,
                    checkedTrackColor = Color.Blue,
                    uncheckedTrackColor = Color.White
                )
            )
        }
    }
}


@Preview
@Composable
fun AddNewCommunityPostScreenPreview() {
//    AddNewCommunityPostScreen(rememberNavController())
}