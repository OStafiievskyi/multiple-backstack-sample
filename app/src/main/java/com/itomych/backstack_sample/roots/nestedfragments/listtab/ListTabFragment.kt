package com.itomych.backstack_sample.roots.nestedfragments.listtab

import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.child.listscreen.UserProfileFragment
import com.itomych.backstack_sample.databinding.FragmentTabListBinding
import com.itomych.backstack_sample.roots.nestedfragments.BaseTabItemContainerFragment
import com.itomych.backstack_sample.viewBindingWithBinder

class ListTabFragment : BaseTabItemContainerFragment(
    R.layout.fragment_tab_list
) {
    private val binding by viewBindingWithBinder(FragmentTabListBinding::bind)

    override fun handleNavigation(it: BaseNavigation) {
        when (it) {
            is BaseNavigation.UserProfile -> {
                openChildFragment(
                    hostId = binding.navHostContainer.id,
                    clazz = UserProfileFragment::class.java,
                    args = it.bundle
                )
            }
            else -> { /*do nothing*/
            }
        }
    }
}