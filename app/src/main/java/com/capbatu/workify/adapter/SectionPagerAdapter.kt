package com.capbatu.workify.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capbatu.workify.ui.addActivity.AddActivityFragment
import com.capbatu.workify.ui.home.HomeFragment
import com.capbatu.workify.ui.listActivity.ListActivityFragment
import com.capbatu.workify.ui.listArticle.ListArticleFragment
import com.capbatu.workify.ui.setting.SettingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ListActivityFragment()
            2 -> fragment = AddActivityFragment()
            3 -> fragment = ListArticleFragment()
            4 -> fragment = SettingFragment()
        }

        return fragment as Fragment
    }
}
