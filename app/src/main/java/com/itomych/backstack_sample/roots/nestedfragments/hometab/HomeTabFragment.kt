package com.itomych.backstack_sample.roots.nestedfragments.hometab

import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.child.homescreen.AboutFragment
import com.itomych.backstack_sample.databinding.FragmentTabHomeBinding
import com.itomych.backstack_sample.roots.nestedfragments.BaseTabItemContainerFragment
import com.itomych.backstack_sample.viewBindingWithBinder

class HomeTabFragment : BaseTabItemContainerFragment(
    R.layout.fragment_tab_home
) {
    private val binding by viewBindingWithBinder(FragmentTabHomeBinding::bind)

    override fun handleNavigation(it: BaseNavigation) {
        when (it) {
            BaseNavigation.About -> {
                openChildFragment(
                    hostId = binding.navHostContainer.id,
                    clazz = AboutFragment::class.java
                )
            }
            else -> { /*do nothing*/
            }
        }
    }
}