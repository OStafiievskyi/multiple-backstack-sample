package com.itomych.backstack_sample.roots.multiplebackstack

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.child.formscreen.RegisterFragment
import com.itomych.backstack_sample.child.formscreen.RegisteredFragment
import com.itomych.backstack_sample.child.homescreen.AboutFragment
import com.itomych.backstack_sample.child.homescreen.WelcomeFragment
import com.itomych.backstack_sample.child.listscreen.UserProfileFragment
import com.itomych.backstack_sample.child.listscreen.UsersFragment
import com.itomych.backstack_sample.databinding.FragmentRootBinding
import com.itomych.backstack_sample.viewBindingWithBinder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MultipleBackStackRootFragment : Fragment(R.layout.fragment_root) {
    private val binding by viewBindingWithBinder(FragmentRootBinding::bind)

    private val rootSaveViewModel by viewModels<MultipleBackStackStateViewModel>()
    private val baseNavigationViewModel by activityViewModels<NavigationViewModel>()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (childFragmentManager.backStackEntryCount > 1) {
                childFragmentManager.popBackStack()
            } else {
                remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToRootNavigation()
        subscribeToChildNavigation()
        if (savedInstanceState == null) {
            setupRootFragments()
            binding.bottomNav.selectedItemId = R.id.home
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }

    private fun subscribeToRootNavigation() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    openRootFragment(WelcomeFragment::class.java)
                }
                R.id.list -> {
                    openRootFragment(UsersFragment::class.java)
                }
                R.id.form -> {
                    openRootFragment(RegisterFragment::class.java)
                }
                else -> false
            }
        }
    }

    private fun <T : Fragment> openRootFragment(clazz: Class<T>): Boolean {
        restoreRootFragment(clazz)
        rootSaveViewModel.lastRoot = clazz.name
        return true
    }

    private fun subscribeToChildNavigation() {
        baseNavigationViewModel.navigationFlow.onEach {
            when (it) {
                BaseNavigation.About -> {
                    addFragment(AboutFragment::class.java)
                }
                BaseNavigation.Registered -> {
                    addFragment(RegisteredFragment::class.java)
                }
                is BaseNavigation.UserProfile -> {
                    addFragment(
                        clazz = UserProfileFragment::class.java,
                        args = it.bundle
                    )
                }
            }
        }.launchIn(lifecycleScope)
    }


    private fun setupRootFragments() {
        addFragment(WelcomeFragment::class.java)
        childFragmentManager.saveBackStack(WelcomeFragment::class.java.name)

        addFragment(UsersFragment::class.java)
        childFragmentManager.saveBackStack(UsersFragment::class.java.name)

        addFragment(RegisterFragment::class.java)
        childFragmentManager.saveBackStack(RegisterFragment::class.java.name)
    }

    private fun <T : Fragment> restoreRootFragment(
        clazz: Class<T>
    ): Boolean {
        rootSaveViewModel.lastRoot?.let {
            if (it != clazz.name) {
                childFragmentManager.saveBackStack(it)
            }
        }
        childFragmentManager.restoreBackStack(clazz.name)
        return true
    }

    private fun <T : Fragment> addFragment(
        clazz: Class<T>,
        args: Bundle? = null
    ) {
        childFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                binding.navHostContainer.id,
                clazz, args, clazz.name
            )
            .addToBackStack(clazz.name)
            .commit()
    }
}