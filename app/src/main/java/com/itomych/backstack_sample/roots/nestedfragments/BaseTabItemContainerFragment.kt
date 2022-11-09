package com.itomych.backstack_sample.roots.nestedfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseTabItemContainerFragment(@LayoutRes layout: Int) :
    Fragment(layout) {

    private val navigationViewModel: NavigationViewModel by activityViewModels()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
            } else {
                remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            backPressedCallback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationViewModel.navigationFlow.onEach {
            handleNavigation(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    abstract fun handleNavigation(it: BaseNavigation)

    protected fun <T : Fragment> openChildFragment(
        hostId: Int,
        clazz: Class<T>,
        args: Bundle? = null
    ) {
        childFragmentManager.beginTransaction()
            .replace(
                hostId,
                clazz, args, null
            )
            .addToBackStack(null)
            .commit()
    }
}