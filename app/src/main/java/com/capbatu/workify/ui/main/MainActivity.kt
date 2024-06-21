package com.capbatu.workify.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.capbatu.workify.R
import com.capbatu.workify.adapter.SectionPagerAdapter
import com.capbatu.workify.databinding.ActivityMainBinding
import com.capbatu.workify.ui.addActivity.AddActivityFragment
import com.capbatu.workify.ui.home.HomeFragment
import com.capbatu.workify.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    HomeFragment.SwipeViewPagerHome,
    AddActivityFragment.SwipeViewPagerAddActivity {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()
    private val sectionPagerAdapter = SectionPagerAdapter(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getAuthSession().observe(this@MainActivity) { session ->
                if (!session.isLogin) {
                    Intent(this@MainActivity, WelcomeActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                } else {
                    setContentView(binding.root)
                    setupDisplay()
                }
            }
        }
    }

    private fun setupDisplay() {
        binding.apply {
            viewModel.currentPage.observe(this@MainActivity) { page ->
                if (viewPager.currentItem == page) {
                    viewPager.setCurrentItem(page, true)
                } else {
                    viewPager.setCurrentItem(page, false)
                }
            }

            bottomNavigation.apply {
                selectedItemId = R.id.item_menu_home

                setOnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.item_menu_home -> viewModel.setCurrentPage(0)
                        R.id.item_menu_activity -> viewModel.setCurrentPage(1)
                        R.id.item_menu_add -> viewModel.setCurrentPage(2)
                        R.id.item_menu_article -> viewModel.setCurrentPage(3)
                        R.id.item_menu_setting -> viewModel.setCurrentPage(4)
                    }
                    true
                }
            }

            viewPager.apply {
                adapter = sectionPagerAdapter

                setCurrentItem(0, false)

                registerOnPageChangeCallback(
                    object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            viewModel.setCurrentPage(position)
                            val itemId =
                                when (position) {
                                    0 -> R.id.item_menu_home
                                    1 -> R.id.item_menu_activity
                                    2 -> R.id.item_menu_add
                                    3 -> R.id.item_menu_article
                                    else -> R.id.item_menu_setting
                                }
                            binding.bottomNavigation.selectedItemId = itemId
                        }
                    },
                )
            }
        }
    }

    override fun swipeToPage(page: Int) {
        binding.apply {
            viewModel.setCurrentPage(page)
        }
    }
}
