package com.itomych.backstack_sample.child.formscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.databinding.FragmentRegisterBinding
import com.itomych.backstack_sample.viewBindingWithBinder


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: NavigationViewModel by activityViewModels()
    private val binding by viewBindingWithBinder(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener {
            viewModel.open(BaseNavigation.Registered)
        }
    }
}
