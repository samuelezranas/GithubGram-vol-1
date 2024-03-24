package com.dicoding.githubgram.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubgram.data.response.ItemsItem
import com.dicoding.githubgram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchView()

        setupRecyclerView()

        observeViewModel()
    }

    private fun setupSearchView() {
        with(binding) {
            svUser.setupWithSearchBar(searchUser)
            svUser
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchUser.text = svUser.text
                    mainViewModel.findGithub(svUser.text.toString())
                    svUser.hide()
                    showLoading(true)
                    false
                }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        mainViewModel.listUser.observe(this) { user ->
            showUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showUserData(user: List<ItemsItem>?) {
        val adapter = UserAdapter(this)
        adapter.submitList(user)
        binding.rvMain.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
