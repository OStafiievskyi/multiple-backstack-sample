package com.itomych.backstack_sample.roots.nestedfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.databinding.FragmentRootBinding
import com.itomych.backstack_sample.roots.nestedfragments.formstab.FormTabFragment
import com.itomych.backstack_sample.roots.nestedfragments.hometab.HomeTabFragment
import com.itomych.backstack_sample.roots.nestedfragments.listtab.ListTabFragment
import com.itomych.backstack_sample.viewBindingWithBinder

class RootTabContainerFragment : Fragment(R.layout.fragment_root) {

    private val rootTags: List<String> = listOf(
        FormTabFragment::class.java.name,
        HomeTabFragment::class.java.name,
        ListTabFragment::class.java.name
    )

    private val binding by viewBindingWithBinder(FragmentRootBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    openRootFragment(HomeTabFragment::class.java)
                }
                R.id.list -> {
                    openRootFragment(ListTabFragment::class.java)
                }
                R.id.form -> {
                    openRootFragment(FormTabFragment::class.java)
                }
                else -> false
            }
        }
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.home
        }
    }

    private fun <T : Fragment> openRootFragment(
        clazz: Class<T>
    ): Boolean {
        val newRootTag = clazz.name
        val transaction =
            childFragmentManager.findFragmentByTag(newRootTag)?.let { newRootFragment ->
                detachRoots(newRootTag).attach(newRootFragment)
            } ?: run {
                detachRoots(newRootTag).add(binding.navHostContainer.id, clazz, null, newRootTag)
            }
        transaction.commit()
        return true
    }

    private fun detachRoots(newRootTag: String): FragmentTransaction {
        var transaction = childFragmentManager.beginTransaction()
        rootTags.filter { it != newRootTag }.forEach {
            childFragmentManager.findFragmentByTag(it)?.let { fragment ->
                transaction = transaction.detach(fragment)
            }
        }
        return transaction
    }
}