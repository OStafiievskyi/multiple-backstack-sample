package com.itomych.backstack_sample.child.listscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.databinding.FragmentUserProfileBinding
import com.itomych.backstack_sample.viewBindingWithBinder

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {
    private val binding by viewBindingWithBinder(FragmentUserProfileBinding::bind)
    private val args by navArgs<UserProfileFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileUserName.text = args.userName
    }
}
