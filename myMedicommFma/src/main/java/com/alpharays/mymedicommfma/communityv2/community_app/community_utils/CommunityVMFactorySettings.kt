package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.alpharays.medico.medico_utils.SavedStateHandleViewModelFactory
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CurrPostCommentsViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.presentation.MessagesViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.usecase.MessagesUseCase
import kotlin.system.exitProcess

@Composable
inline fun <reified T : ViewModel> getCommunityViewModel(useCase: Any): T {
    return viewModel(
        factory = CommunityAppViewModelFactory(useCase)
    )
}

@Composable
inline fun <reified T : ViewModel> getCommunitySSHViewModel(useCase: Any): T {
    val owner = LocalSavedStateRegistryOwner.current
    return viewModel(
        factory = SavedStateHandleViewModelFactory(useCase, owner)
    )
}

@Suppress("UNCHECKED_CAST")
class CommunityAppViewModelFactory<T>(private val useCase: T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CommunityViewModel::class.java) -> {
                CommunityViewModel(useCase as CommunityUseCase) as T
            }

            modelClass.isAssignableFrom(CurrPostCommentsViewModel::class.java) -> {
                CurrPostCommentsViewModel(useCase as CommunityUseCase) as T
            }

            modelClass.isAssignableFrom(MessagesViewModel::class.java) -> {
                MessagesViewModel(useCase as MessagesUseCase) as T
            }

            else -> {
                Log.e("SOME_VIEW_MODEL", "NOT DEFINED")
                exitProcess(1)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SavedStateHandleViewModelFactory(
    private val useCase: Any,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return when {
            // depends on need
            modelClass.isAssignableFrom(CurrPostCommentsViewModel::class.java) -> {
                CurrPostCommentsViewModel(useCase as CommunityUseCase) as T
            }

            else -> {
                Log.e("SOME_VIEW_MODEL", "NOT DEFINED")
                exitProcess(1)
            }
        }
    }
}
