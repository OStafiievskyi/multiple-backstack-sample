package com.itomych.backstack_sample

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NavigationViewModel(application: Application) : AndroidViewModel(application) {
    private val navigationMutableFlow: MutableSharedFlow<BaseNavigation> = MutableSharedFlow()

    val navigationFlow: SharedFlow<BaseNavigation>
        get() = navigationMutableFlow.asSharedFlow()

    fun open(navigation: BaseNavigation) {
        viewModelScope.launch {
            navigationMutableFlow.emit(navigation)
        }
    }
}


sealed class BaseNavigation {
    object Registered : BaseNavigation()
    object About : BaseNavigation()
    data class UserProfile(val bundle: Bundle) : BaseNavigation()
}