package com.dicoding.githubgram.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubgram.data.response.DetailGithubResponse
import com.dicoding.githubgram.data.response.ItemsItem
import com.dicoding.githubgram.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<DetailGithubResponse?>()
    val userDetail: LiveData<DetailGithubResponse?> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFollower = MutableLiveData<List<ItemsItem>?>()
    val userFollower: LiveData<List<ItemsItem>?> = _userFollower

    private val _userFollowing = MutableLiveData<List<ItemsItem>?>()
    val userFollowing: LiveData<List<ItemsItem>?> = _userFollowing

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailGithubResponse> {
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message ?: "Unknown error")
            }
        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollower.value = response.body()
                } else {
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message ?: "Unknown error")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                } else {
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message ?: "Unknown error")
            }
        })
    }
}
