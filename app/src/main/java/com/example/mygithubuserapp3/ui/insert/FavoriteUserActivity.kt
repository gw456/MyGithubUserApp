package com.example.mygithubuserapp3.ui.insert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuserapp3.R
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.databinding.ActivityFavoriteUserBinding
import com.example.mygithubuserapp3.ui.ListUserAdapter
import com.example.mygithubuserapp3.ui.RoomViewModelFactory
import com.example.mygithubuserapp3.ui.roomViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_user)

        val layoutManager = LinearLayoutManager(this)
        binding.rvAccount.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvAccount.addItemDecoration(itemDecoration)

        val factory: RoomViewModelFactory = RoomViewModelFactory.getInstance(this)
        val viewModel: roomViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteUser().observe(this) { account ->
            setUserDetail(account)
        }
    }

    private fun setUserDetail(account: List<User>) {
        val adapter = ListUserAdapter(account)
        binding.rvAccount.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@FavoriteUserActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data)
                startActivity(intentToDetail)
            }
        })
    }
}