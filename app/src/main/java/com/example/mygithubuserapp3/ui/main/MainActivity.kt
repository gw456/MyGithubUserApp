package com.example.mygithubuserapp3.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuserapp3.R
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.data.remote.response.ItemsItem
import com.example.mygithubuserapp3.databinding.ActivityMainBinding
import com.example.mygithubuserapp3.ui.ListUserAdapter
import com.example.mygithubuserapp3.ui.MainViewModel
import com.example.mygithubuserapp3.ui.RoomViewModelFactory
import com.example.mygithubuserapp3.ui.insert.DetailActivity
import com.example.mygithubuserapp3.ui.insert.DialogActivity
import com.example.mygithubuserapp3.ui.insert.FavoriteUserActivity
import com.example.mygithubuserapp3.ui.roomViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.main_title)

        val layoutManager = LinearLayoutManager(this)
        binding.rvAccount.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvAccount.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText == "") {
                    binding.rvAccount.adapter = null
                }
                return false
            }
        })

        mainViewModel.listUser.observe(this) { account ->
            setUserDetail(account)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.snackbarEror.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUserDetail(items: List<ItemsItem>) {
        val listUser = ArrayList<User>()

        items.forEach { account ->
            val user = User(
                account.login,
                account.avatarUrl,
                false
            )
            listUser.add(user)
        }

        val adapter = ListUserAdapter(listUser)
        binding.rvAccount.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data)
                startActivity(intentToDetail)
            }

        })
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
                val intentToDialog = Intent(this@MainActivity, DialogActivity::class.java)
                startActivity(intentToDialog)
            }
            R.id.favorites -> {
                val intentToFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intentToFavorite)
            }
        }
        return true
    }
}