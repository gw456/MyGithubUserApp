package com.example.mygithubuserapp3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.ui.insert.FollowersFragment
import com.example.mygithubuserapp3.ui.insert.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val user: User?) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                val mBundleFollowersFragment = Bundle()
                mBundleFollowersFragment.putParcelable(FollowersFragment.DATA, user)
                fragment.arguments = mBundleFollowersFragment
            }
            1 -> {
                fragment = FollowingFragment()
                val mBundleFollowingFragment = Bundle()
                mBundleFollowingFragment.putParcelable(FollowingFragment.DATA, user)
                fragment.arguments = mBundleFollowingFragment
            }
        }
        return fragment as Fragment
    }
}