package com.autismbug.hello.viewpage2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.autismbug.hello.viewpage2.adapter.FragmentPagerAdapter
import com.autismbug.hello.viewpage2.transformer.DepthPageTransformer
import com.autismbug.hello.viewpage2.transformer.ScaleInTransformer
import com.autismbug.hello.viewpage2.transformer.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPage2TablayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout_view_page2)

        val viewPager2 = findViewById<ViewPager2>(R.id.pager)

        val fragmentPagerAdapter = FragmentPagerAdapter(this)
        viewPager2.apply {
            adapter = fragmentPagerAdapter
            // 不提前加载
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            // page 间过渡效果
//            setPageTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))
            //深度变化效果
//            setPageTransformer(DepthPageTransformer())
//            // 比例放大进入效果
//            setPageTransformer(ScaleInTransformer())
//            // 缩放进入退出效果
            setPageTransformer(ZoomOutPageTransformer())

        }

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            //设置标签名称
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }
}