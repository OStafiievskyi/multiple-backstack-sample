package com.itomych.backstack_sample.child.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.itomych.backstack_sample.BaseNavigation
import com.itomych.backstack_sample.NavigationViewModel
import com.itomych.backstack_sample.R
import com.itomych.backstack_sample.databinding.FragmentUsersBinding
import com.itomych.backstack_sample.databinding.ListViewItemBinding
import com.itomych.backstack_sample.viewBindingWithBinder

class UsersFragment : Fragment(R.layout.fragment_users) {
    private val viewModel: NavigationViewModel by activityViewModels()
    private val binding by viewBindingWithBinder(FragmentUsersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewAdapter = UsersAdapter(Array(10) { "Person ${it + 1}" }) {
            viewModel.open(BaseNavigation.UserProfile(it))
        }

        binding.leaderboardList.apply {
            setHasFixedSize(true)
            adapter = viewAdapter

        }
    }

}

class UsersAdapter(
    private val data: Array<String>,
    private val clickFunc: (bundle: Bundle) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListViewItemBinding) : RecyclerView.ViewHolder(binding.root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)


        return ViewHolder(
            ListViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            userNameText.text = data[position]
            userAvatarImage.setImageResource(listOfAvatars[position % listOfAvatars.size])
            root.setOnClickListener {
                val bundle = bundleOf(USERNAME_KEY to data[position])
                clickFunc.invoke(bundle)
            }
        }
    }

    override fun getItemCount() = data.size

    companion object {
        const val USERNAME_KEY = "userName"
    }
}

private val listOfAvatars = listOf(
    R.drawable.avatar_1_raster,
    R.drawable.avatar_2_raster,
    R.drawable.avatar_3_raster,
    R.drawable.avatar_4_raster,
    R.drawable.avatar_5_raster,
    R.drawable.avatar_6_raster
)
