package com.alpharays.medcomm

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.alpharays.medcomm.ui.theme.MedCommTheme
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedCommTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
            openMediCommFma(context)
        },
        textDecoration = TextDecoration.Underline
    )
}

fun setupMedComm(context: Context) {
    CommunityUtils.setAuthToken(context, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1Mjc3N2Q4MjVmZmMyNTA3MmE5NDBiNyIsImlhdCI6MTcwOTU1NzA4NH0.C0lTg0jf6gtUfBFGX7DAMJQ8kVKK0FboDC547pkC4Is")
}

fun openMediCommFma(context: Context) {
    MedCommRouter.initiateMedCommRouter(context)
    setupMedComm(context)
    MedCommRouter.startDummyActivity(context)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MedCommTheme {
        Greeting("Android")
    }
}