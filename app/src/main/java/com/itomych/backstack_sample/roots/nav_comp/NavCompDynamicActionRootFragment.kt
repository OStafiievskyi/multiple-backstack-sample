package com.itomych.backstack_sample.roots.nav_comp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.child.formscreen.RegisterFragmentDirections
import com.itomych.backstack_sample.child.homescreen.WelcomeFragmentDirections
import com.itomych.backstack_sample.child.listscreen.UsersFragmentDirections
import com.itomych.backstack_sample.databinding.FragmentRootBinding
import com.itomych.backstack_sample.viewBindingWithBinder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NavCompDynamicActionRootFragment :
    Fragment(R.layout.fragment_nav_host) {

    private val binding by viewBindingWithBinder(FragmentRootBinding::bind)

    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = binding.navHostContainer.getFragment<NavHostFragment>().navController
        binding.bottomNav.setOnItemSelectedListener {
            navigate(navController, it)
        }
        navigationViewModel.navigationFlow.onEach {
            when (it) {
                BaseNavigation.About -> navController.navigate(WelcomeFragmentDirections.actionWelcomeToAbout())
                BaseNavigation.Registered -> navController.navigate(RegisterFragmentDirections.actionRegisterToRegistered())
                is BaseNavigation.UserProfile -> navController.navigate(
                    UsersFragmentDirections.actionUsersToUserProfile(
                        it.bundle.getString("userName", "")
                    )
                )
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.navigateUp()) {
                    remove()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun navigate(navController: NavController, item: MenuItem): Boolean {
        val builder = NavOptions.Builder().setRestoreState(true)
        builder.setPopUpTo(
            navController.graph.findStartDestination().id,
            inclusive = false,
            saveState = true
        )
        val options = builder.build()
        return try {
            navController.navigate(item.itemId, null, options)
            navController.currentDestination?.hierarchy?.any { it.id == item.itemId } == true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}