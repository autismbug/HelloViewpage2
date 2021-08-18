package com.autismbug.hello.viewpage2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.autismbug.hello.viewpage2.adapter.FragmentPagerAdapter
import com.autismbug.hello.viewpage2.transformer.ScaleInTransformer

class ViewPage2FragmentActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page2_fragment)

        viewPager2 = findViewById(R.id.view_pager)

        fragmentPagerAdapter = FragmentPagerAdapter(this)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
//        compositePageTransformer.addTransformer(DepthPageTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))
        viewPager2.apply {
            adapter = fragmentPagerAdapter
            // 不提前加载
//            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

            // 一屏多页
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.dp_10) +
                        resources.getDimensionPixelOffset(R.dimen.dp_10)
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }

            //单个 page 间效果
//            setPageTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))
            setPageTransformer(compositePageTransformer)
        }

    }
}