package com.dicoding.githubgram.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubgram.data.response.ItemsItem
import com.dicoding.githubgram.databinding.FragmentSectionBinding

class SectionFragment : Fragment() {
    private val detailViewModel by activityViewModels<DetailViewModel>()

    private lateinit var sectionBinding: FragmentSectionBinding
    private var position: Int? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sectionBinding = FragmentSectionBinding.inflate(layoutInflater)
        return sectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        detailViewModel.getFollower(username.toString())
        detailViewModel.getFollowing(username.toString())

        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) { followers ->
                followers?.let { setFollowersData(it) }

            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) { following ->
                following?.let { setFollowingData(it) }

            }
        }
    }

    private fun setFollowingData(following: List<ItemsItem>) {
        sectionBinding.rvSectionPage.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = DetailUserAdapter()
        adapter.submitList(following)
        sectionBinding.rvSectionPage.adapter = adapter
        sectionBinding.rvSectionPage.visibility = View.VISIBLE
    }

    private fun setFollowersData(followers: List<ItemsItem>) {
        sectionBinding.rvSectionPage.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = DetailUserAdapter()
        adapter.submitList(followers)
        sectionBinding.rvSectionPage.adapter = adapter
        sectionBinding.rvSectionPage.visibility = View.VISIBLE
    }

    companion object {
        const val ARG_POSITION: String = "arg_position"
        const val ARG_USERNAME: String = "arg_username"
    }
}