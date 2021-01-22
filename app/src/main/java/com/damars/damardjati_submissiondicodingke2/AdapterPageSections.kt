package com.damars.damardjati_submissiondicodingke2

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.annotation.Nullable

class AdapterPageSections(private val mainContext: Context, fragM: FragmentManager): FragmentPagerAdapter(fragM, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int { return 2 }

    companion object {
        private val Title_Tab = intArrayOf(
                R.string.follower,
                R.string.following)
    }
    override fun getItem(position: Int): Fragment {
        var fragments: Fragment? = null
        when (position) {
            0 -> fragments = ForFragmentFollower()
            1 -> fragments = ForFragmentFollowing()
        }
        return fragments as Fragment
    }
    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return mainContext.resources.getString(Title_Tab[position])
    }
}