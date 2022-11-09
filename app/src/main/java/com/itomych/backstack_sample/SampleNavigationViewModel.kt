package com.itomych.backstack_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class SampleNavigationViewModel() : ViewModel() {
    private val navigationMutableFlow: MutableSharedFlow<SampleNavigation> = MutableSharedFlow()

    val navigationFlow: SharedFlow<SampleNavigation>
        get() = navigationMutableFlow.asSharedFlow()

    fun open(navigation: SampleNavigation) {
        viewModelScope.launch {
            navigationMutableFlow.emit(navigation)
        }
    }
}


sealed class SampleNavigation {
    object WithoutMultipleBackStack : SampleNavigation()
    object NestedRoot : SampleNavigation()
    object MultipleBackStack : SampleNavigation()
    object NavigationComponentDefault : SampleNavigation()
    object NavigationComponentMultipleBackStack : SampleNavigation()
    object NavigationComponentMultipleBackStackDynamic : SampleNavigation()
}