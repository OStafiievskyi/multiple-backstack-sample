package com.itomych.backstack_sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.itomych.backstack_sample.databinding.FragmentChooseSampleBinding

class ChooseSampleFragment : Fragment(R.layout.fragment_choose_sample) {
    private val sampleNavigationViewModel by activityViewModels<SampleNavigationViewModel>()
    private val binding by viewBindingWithBinder(FragmentChooseSampleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnMultipleBackStack.setOnClickListener {
                sampleNavigationViewModel.open(SampleNavigation.MultipleBackStack)
            }
            btnNavigationComponentDefault.setOnClickListener {
                sampleNavigationViewModel.open(SampleNavigation.NavigationComponentDefault)
            }
            btnNestedRootFragments.setOnClickListener {
                sampleNavigationViewModel.open(SampleNavigation.NestedRoot)
            }
            btnWithoutMultipleBackStack.setOnClickListener {
                sampleNavigationViewModel.open(SampleNavigation.WithoutMultipleBackStack)
            }
            btnNavigationComponentMultipleBackStack.setOnClickListener {
                sampleNavigationViewModel.open(SampleNavigation.NavigationComponentMultipleBackStack)
            }
            btnNavigationComponentMultipleBackStackDynamic.setOnClickListener {
                sampleNavigationViewModel.open(SampleNavigation.NavigationComponentMultipleBackStackDynamic)
            }
        }
    }
}