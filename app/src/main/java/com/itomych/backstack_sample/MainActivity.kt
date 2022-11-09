package com.itomych.backstack_sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.itomych.backstack_sample.databinding.ActivityMainBinding
import com.itomych.backstack_sample.roots.WithoutMultipleBackStackRootFragment
import com.itomych.backstack_sample.roots.multiplebackstack.MultipleBackStackRootFragment
import com.itomych.backstack_sample.roots.nav_comp.NavCompDefaultRootFragment
import com.itomych.backstack_sample.roots.nav_comp.NavCompDynamicActionRootFragment
import com.itomych.backstack_sample.roots.nav_comp.NavCompXmlActionRootFragment
import com.itomych.backstack_sample.roots.nestedfragments.RootTabContainerFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sampleNavigationViewModel by viewModels<SampleNavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sampleNavigationViewModel.navigationFlow.onEach {
            when (it) {
                SampleNavigation.NestedRoot -> {
                    openChildFragment(RootTabContainerFragment::class.java)
                }
                SampleNavigation.WithoutMultipleBackStack -> {
                    openChildFragment(WithoutMultipleBackStackRootFragment::class.java)
                }
                SampleNavigation.MultipleBackStack -> {
                    openChildFragment(MultipleBackStackRootFragment::class.java)
                }
                SampleNavigation.NavigationComponentDefault -> {
                    openChildFragment(NavCompDefaultRootFragment::class.java)
                }
                SampleNavigation.NavigationComponentMultipleBackStack -> {
                    openChildFragment(NavCompXmlActionRootFragment::class.java)
                }
                SampleNavigation.NavigationComponentMultipleBackStackDynamic -> {
                    openChildFragment(NavCompDynamicActionRootFragment::class.java)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun <T : Fragment> openChildFragment(clazz: Class<T>) {
        supportFragmentManager.beginTransaction()
            .replace(
                binding.navHostContainer.id,
                clazz, null, null
            )
            .addToBackStack(null)
            .commit()
    }
}