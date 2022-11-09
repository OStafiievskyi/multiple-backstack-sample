package com.itomych.backstack_sample.roots

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
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

class WithoutMultipleBackStackRootFragment : Fragment(R.layout.fragment_root) {
    private val viewModel: NavigationViewModel by activityViewModels()

    private val binding by viewBindingWithBinder(FragmentRootBinding::bind)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            backPressedCallback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationFlow.onEach {
            when (it) {
                BaseNavigation.About -> {
                    openChildFragment(AboutFragment::class.java)
                }
                BaseNavigation.Registered -> {
                    openChildFragment(RegisteredFragment::class.java)
                }
                is BaseNavigation.UserProfile -> {
                    openChildFragment(UserProfileFragment::class.java, it.bundle)
                }
            }
        }.launchIn(lifecycleScope)
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
        binding.bottomNav.selectedItemId = R.id.home
    }

    private fun <T : Fragment> openRootFragment(
        clazz: Class<T>,
        backStackTag: String = "ROOT_TAG"
    ): Boolean {
        childFragmentManager.popBackStackImmediate(
            backStackTag,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        childFragmentManager.beginTransaction()
            .replace(
                binding.navHostContainer.id,
                clazz, null, clazz.name
            )
            .addToBackStack(backStackTag)
            .commit()
        return true
    }

    private fun <T : Fragment> openChildFragment(clazz: Class<T>, args: Bundle? = null) {
        childFragmentManager.beginTransaction()
            .replace(
                binding.navHostContainer.id,
                clazz, args, null
            )
            .addToBackStack(null)
            .commit()
    }


}