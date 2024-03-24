package com.dicoding.githubgram.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubgram.R
import com.dicoding.githubgram.data.response.DetailGithubResponse
import com.dicoding.githubgram.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        name = intent.getStringExtra(USER_NAME)
        detailViewModel.getDetail(name.toString())

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.userDetail.observe(this) { user ->
            if (user != null) {
                setDetailData(user)
            }
        }
    }

    private fun setDetailData(user: DetailGithubResponse) {
        detailBinding.apply {
            tvNameDetail.visibility = View.VISIBLE
            tvNameDetail.text = user.name

            tvUsernameDetail.visibility = View.VISIBLE
            tvUsernameDetail.text = getString(R.string.fill_username, user.login)

            tvFollowersDetail.visibility = View.VISIBLE
            tvFollowersDetail.text = getString(R.string.fill_followers, user.followers)

            tvFollowingDetail.visibility = View.VISIBLE
            tvFollowingDetail.text = getString(R.string.fill_following, user.following)

            imUserDetail.visibility = View.VISIBLE
            Glide.with(imUserDetail.context).load(user.avatarUrl)
                .into(imUserDetail)

            viewPager.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
        }

        val sectionPageAdapter = SectionPageAdapter(this)
        sectionPageAdapter.username = name.toString()

        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionPageAdapter

        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailBinding.progressBar.visibility = View.VISIBLE
        } else {
            detailBinding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val USER_NAME = ""
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }
}