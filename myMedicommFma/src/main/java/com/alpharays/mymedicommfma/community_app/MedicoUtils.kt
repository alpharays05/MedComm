package com.alpharays.mymedicommfma.community_app

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
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
import com.alpharays.mymedicommfma.common.basesdk.BaseSDK

import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.common.nuttiessdk.MedicommConstants
import com.alpharays.mymedicommfma.common.nuttiessdk.MedicommConstants.MEDICO_DOC_ID
import com.alpharays.mymedicommfma.common.nuttiessdk.MedicommConstants.MEDICO_DOC_ID_KEY
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.CommunityViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon.Builder
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect

class MedicoUtils {
    companion object {
        val context = BaseSDK.getContext()
        
       

    

       
      

       

        object NetworkCheck{
            private var alreadyLost = false
            fun setNetworkStatus(status: Boolean){
                alreadyLost = status
            }
            fun isNetworkAlreadyLost(): Boolean{
                return alreadyLost
            }
        }

        object Balloon{
            private var shown = false
            fun isBalloonShown() = shown
            fun setBalloonStatus(status: Boolean){
                shown = status
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

        @Composable
        fun <T> ComposableNoNetworkFound(context: Context, modifier: Modifier, viewModel: T, toShow: Boolean = true) {
            var reLoadScreen by remember { mutableStateOf(false) }
            if(toShow){
                val painter = painterResource(id = com.google.android.material.R.drawable.ic_mtrl_chip_checked_black)
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
                        text = MedicommConstants.SOMETHING_WENT_WRONG,
                        style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W600, textAlign = TextAlign.Center)
                    )

                    Text(
                        modifier = modifier,
                        text = MedicommConstants.NO_CONNECTION_MSG,
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
               //     MedicoToast.showToast(context, NO_CONNECTION) todo
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

        @Composable
        fun balloon(): Builder {
            val builder = rememberBalloonBuilder {
                setArrowSize(10)
                setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                setArrowPosition(0.75f)
                setArrowOrientation(ArrowOrientation.TOP)
                setWidth(BalloonSizeSpec.WRAP)
                setHeight(BalloonSizeSpec.WRAP)
                setPadding(9)
                setCornerRadius(8f)
                setBackgroundColorResource(com.google.android.material.R.color.m3_ref_palette_black)
                setAutoDismissDuration(4000L)
                setBalloonAnimation(BalloonAnimation.ELASTIC)
                setIsVisibleOverlay(true)
                setOverlayColorResource(com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
               // setOverlayPaddingResource(R.dimen.overlayPaddingResource)
                setBalloonHighlightAnimation(BalloonHighlightAnimation.BREATH)
//                setOverlayShape(
//                    BalloonOverlayRoundRect(  todo
////                        R.dimen.balloonOverlayRadius,
////                        R.dimen.balloonOverlayRadius
//                    )
//                )
                setDismissWhenClicked(true)
            }
            return builder
        }
    }
}