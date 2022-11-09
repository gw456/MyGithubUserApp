package com.example.mygithubuserapp3.ui.insert

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithubuserapp3.R
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.data.remote.response.UserFollowingResponseItem
import com.example.mygithubuserapp3.ui.ListUserAdapter
import com.example.mygithubuserapp3.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FollowingFragment : Fragment() {

    private lateinit var listUserFollowing: ArrayList<User>
    private lateinit var rvFollowing: RecyclerView
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing = view.findViewById(R.id.rv_following)
        val layoutManager = LinearLayoutManager(activity)
        rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        rvFollowing.addItemDecoration(itemDecoration)

        val data = arguments?.getParcelable<User>(DATA)
        if (data != null) {
            val username = data.username

            mainViewModel.findFollowing(username)
            mainViewModel.listFollowing.observe(requireActivity()) { account ->
                setFollowingDetail(account)
            }
            mainViewModel.isLoading.observe(requireActivity()) {
                showLoading(it)
            }
            mainViewModel.snackbarEror.observe(requireActivity()) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(
                        requireActivity().window.decorView.rootView,
                        snackBarText,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setFollowingDetail(responseBody: List<UserFollowingResponseItem>) {
        listUserFollowing = ArrayList()

        responseBody.forEach { account ->
            val user = User(
                account.login,
                account.avatarUrl,
                false
            )
            listUserFollowing.add(user)
        }

        val adapter = ListUserAdapter(listUserFollowing)
        rvFollowing.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(activity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        val progressBar: ProgressBar = requireView().findViewById(R.id.progressBar)
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val DATA = "data"
    }

}