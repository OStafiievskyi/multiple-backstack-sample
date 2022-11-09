package com.itomych.backstack_sample.roots.nav_comp

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.itomych.backstack_sample.*
import com.itomych.backstack_sample.child.formscreen.RegisterFragmentDirections
import com.itomych.backstack_sample.child.homescreen.WelcomeFragmentDirections
import com.itomych.backstack_sample.child.listscreen.UsersFragmentDirections
import com.itomych.backstack_sample.databinding.FragmentRootBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NavCompXmlActionRootFragment :
    Fragment(R.layout.fragment_nav_root_multiple_backstack) {

    private val binding by viewBindingWithBinder(FragmentRootBinding::bind)

    private val navigationViewModel by activityViewModels<NavigationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = binding.navHostContainer.getFragment<NavHostFragment>().navController
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(NavGraphMultipleDirections.home())
                    true
                }
                R.id.list -> {
                    navController.navigate(NavGraphMultipleDirections.list())
                    true
                }
                R.id.form -> {
                    navController.navigate(NavGraphMultipleDirections.form())
                    true
                }
                else -> false
            }
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
}