package com.itomych.backstack_sample.child.homescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.databinding.FragmentWelcomeBinding
import com.itomych.backstack_sample.viewBindingWithBinder

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private val viewModel: NavigationViewModel by activityViewModels()
    private val binding by viewBindingWithBinder(FragmentWelcomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.aboutBtn.setOnClickListener {
            viewModel.open(BaseNavigation.About)
        }
    }
}
