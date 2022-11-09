package com.itomych.backstack_sample.roots.nestedfragments.formstab

import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.child.formscreen.RegisteredFragment
import com.itomych.backstack_sample.databinding.FragmentTabFormBinding
import com.itomych.backstack_sample.roots.nestedfragments.BaseTabItemContainerFragment
import com.itomych.backstack_sample.viewBindingWithBinder

class FormTabFragment : BaseTabItemContainerFragment(
    R.layout.fragment_tab_form
) {
    private val binding by viewBindingWithBinder(FragmentTabFormBinding::bind)

    override fun handleNavigation(it: BaseNavigation) {
        when (it) {
            BaseNavigation.Registered -> {
                openChildFragment(
                    hostId = binding.navHostContainer.id,
                    clazz = RegisteredFragment::class.java
                )
            }
            else -> { /*do nothing*/ }
        }
    }
}