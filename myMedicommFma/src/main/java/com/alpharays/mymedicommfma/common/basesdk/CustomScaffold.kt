package com.alpharays.mymedicommfma.common.basesdk

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.common.basesdk.CustomBottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    isBottomBarPresent: Boolean = true,
    isTopBarPresent: Boolean = false,
    navController: NavController,
    topBarContent: @Composable () -> Unit = {},
    content: @Composable (padding: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isTopBarPresent) {
                topBarContent()
            }
        },
        bottomBar = {
            if (isBottomBarPresent) {
                CustomBottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}