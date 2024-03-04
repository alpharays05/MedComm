package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.rememberBalloonBuilder

class CommunityUtils {
    companion object {
        val context = MedCommRouter.context
        fun setAuthToken(token: String) {
            val authTokenSharedPref = context.getSharedPreferences(
                MedCommRouter.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            authTokenSharedPref.edit().putString(MedCommRouter.AUTH_TOKEN_KEY, token).apply()
        }

        fun getAuthToken(): String {
            val authTokenSharedPref = context.getSharedPreferences(
                MedCommRouter.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            return authTokenSharedPref.getString(MedCommRouter.AUTH_TOKEN_KEY, null).toString()
        }

        fun setOneTimePostId(postId: String) {
            val postIdSharedPref = context.getSharedPreferences(
                MedCommRouter.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            postIdSharedPref.edit().putString(MedCommRouter.ONE_TIME_POST_ID_KEY, postId).apply()
        }

        fun getOneTimePostId(): String {
            val postIdSharedPref = context.getSharedPreferences(
                MedCommRouter.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            return postIdSharedPref.getString(MedCommRouter.ONE_TIME_POST_ID_KEY, "").toString()
        }

        @Composable
        fun <T> ComposableNoNetworkFound(context: Context, modifier: Modifier, viewModel: T, toShow: Boolean = true) {
            var reLoadScreen by remember { mutableStateOf(false) }
            if(toShow){
                val painter = painterResource(id = R.drawable.no_internet)
                Column(
                    modifier = modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painter,
                        contentDescription = "No internet found",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(200.dp)
                    )

                    Text(
                        modifier = modifier,
                        text = MedCommRouter.SOMETHING_WENT_WRONG,
                        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W600, textAlign = TextAlign.Center)
                    )

                    Text(
                        modifier = modifier,
                        text = MedCommRouter.NO_CONNECTION_MSG,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W300, textAlign = TextAlign.Center)
                    )

                    OutlinedButton(
                        shape = RoundedCornerShape(12.dp),
                        modifier = modifier,
                        onClick = {
                            reLoadScreen = true
                        }) {
                        Text(
                            text = "Refresh",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = Color.Blue
                        )
                    }
                }
            }

            if(reLoadScreen){
                if(isInternetAvailable(context) == ConnectivityObserver.Status.Unavailable){
                    MedCommToast.showToast(context, NO_CONNECTION)
                    reLoadScreen = false
                    return
                }
                ScreenReload(viewModel)
            }
        }

        @Composable
        fun <T> ScreenReload(viewModel: T) {
            LaunchedEffect(Unit){
                when(viewModel){
                    is CommunityViewModel -> {
                        viewModel.retryGettingPosts()
                    }
                }
            }
        }


        private lateinit var connectivityObserver: ConnectivityObserver
        @Composable
        fun isInternetAvailable(context: Context): ConnectivityObserver.Status {
            connectivityObserver = NetworkConnectivityObserver(context)
            val status by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )
            return status
        }

        fun getMedicoColor(context: Context, color: Int): Int {
            return context.getColor(color)
        }

        object NetworkCheck{
            private var alreadyLost = false
            fun setNetworkStatus(status: Boolean){
                alreadyLost = status
            }
            fun isNetworkAlreadyLost(): Boolean{
                return alreadyLost
            }
        }

    }
}