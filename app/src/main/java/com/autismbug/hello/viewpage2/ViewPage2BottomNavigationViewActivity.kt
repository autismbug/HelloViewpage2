package com.autismbug.hello.viewpage2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.autismbug.hello.viewpage2.adapter.FragmentPagerAdapter
import com.autismbug.hello.viewpage2.transformer.ScaleInTransformer
import com.google.android.material.bottomnavigation.BottomNavigationView

class ViewPage2BottomNavigationViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page2_bottom_navigation_view)

        // 页面适配器
        val fragmentPagerAdapter = FragmentPagerAdapter(this)
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        viewPager2.apply {
            adapter = fragmentPagerAdapter
            // 不提前加载
            offscreenPageLimit = fragmentPagerAdapter.itemCount
            // 单动画效果
            setPageTransformer(ScaleInTransformer())
        }

        // 底部菜单
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        // 设置监听
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_messages -> {
                    viewPager2.setCurrentItem(0, true)
                    true
                }
                R.id.menu_contacts -> {
                    viewPager2.setCurrentItem(1, true)
                    true
                }
                R.id.menu_setting -> {
                    viewPager2.setCurrentItem(2, true)
                    true
                }
                R.id.menu_mine -> {
                    viewPager2.setCurrentItem(3, true)
                    true
                }
                else -> throw IllegalArgumentException("未设置的 menu position，请检查参数")
            }
        }

        // 监听页面变化
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }
}