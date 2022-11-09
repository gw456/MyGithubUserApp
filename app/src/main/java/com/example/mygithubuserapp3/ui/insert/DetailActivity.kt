package com.example.mygithubuserapp3.ui.insert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mygithubuserapp3.R
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.data.remote.response.UserDetailResponse
import com.example.mygithubuserapp3.databinding.ActivityDetailBinding
import com.example.mygithubuserapp3.ui.MainViewModel
import com.example.mygithubuserapp3.ui.RoomViewModelFactory
import com.example.mygithubuserapp3.ui.SectionPagerAdapter
import com.example.mygithubuserapp3.ui.roomViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_title)
        val factory: RoomViewModelFactory = RoomViewModelFactory.getInstance(this)
        val viewModel: roomViewModel by viewModels {
            factory
        }

        val data = intent?.getParcelableExtra<User>(DATA)
        if (data != null) {
            val login = data.username
            mainViewModel.findDetailUser(login)
            mainViewModel.detailUser.observe(this) { account ->
                setUserDetail(account)
            }
            mainViewModel.isLoading.observe(this) {
                showLoading(it)
            }
            mainViewModel.snackbarEror.observe(this) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(
                        this.window.decorView.rootView,
                        snackBarText,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            binding.favorites.apply {
                viewModel.isFavoriteUser(data.username).observe(this@DetailActivity){ favoriteState ->
                    if (favoriteState) {
                        this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_baseline_clear_24))
                        setOnClickListener {
                            this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_baseline_add_24))
                            viewModel.updateFavoriteUser(data, false)
                            viewModel.deleteFavoriteUser(data)
                        }
                    } else {
                        setOnClickListener {
                            this.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_baseline_clear_24))
                            viewModel.updateFavoriteUser(data, true)
                            viewModel.insertFavoriteUser(data)
                        }
                    }
                }
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this@DetailActivity, data)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setUserDetail(responseBody: UserDetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(responseBody.avatarUrl)
                .circleCrop()
                .into(detailAvatar)
            val name = responseBody?.name ?: "Not Specified Name"
            detailUserName.text = StringBuilder("$name as ${responseBody.login}")
            company.text = responseBody.company?.toString() ?: "Not Specified Company"
            location.text = responseBody.location?.toString() ?: "Not Specified Country"
            numberFollowers.text = responseBody.followers.toString()
            numberFollowing.text = responseBody.following.toString()
            numberRepo.text = responseBody.publicRepos.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding. progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intentToDialog = Intent(this@DetailActivity, DialogActivity::class.java)
                startActivity(intentToDialog)
            }
            R.id.favorites -> {
                val intentToFavorite = Intent(this@DetailActivity, FavoriteUserActivity::class.java)
                startActivity(intentToFavorite)
            }
        }
        return true
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val TAG = "DetailActivity"
        const val DATA = "data"
    }
}