package com.itomych.backstack_sample.roots.nav_comp

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.child.formscreen.RegisterFragmentDirections
import com.itomych.backstack_sample.child.homescreen.WelcomeFragmentDirections
import com.itomych.backstack_sample.child.listscreen.UsersFragmentDirections
import com.itomych.backstack_sample.databinding.FragmentNavHostBinding
import com.itomych.backstack_sample.viewBindingWithBinder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NavCompDefaultRootFragment : Fragment(R.layout.fragment_nav_host) {
    private val binding by viewBindingWithBinder(FragmentNavHostBinding::bind)
    private val navigationViewModel: NavigationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = binding.navHostContainer.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

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
                val previousBackStackEntry = navController.previousBackStackEntry
                if (previousBackStackEntry == null) {
                    remove()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                } else {
                    navController.navigateUp()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}