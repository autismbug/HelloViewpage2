package com.autismbug.hello.viewpage2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.autismbug.hello.viewpage2.adapter.ViewAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class ViewPage2NormalUseActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2

    companion object {
        private const val TAG = "ViewPage2NormalActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpage2_normal_use)

        val viewAdapter = ViewAdapter()
        viewAdapter.data = listOf(1, 2, 3, 4)
        viewPager2 = findViewById(R.id.view_pager)
        viewPager2.apply {
            adapter = viewAdapter
            // 页面、状态、滚动监听
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    Log.d(
                        TAG,
                        "onPageScrolled() called with: position = $position, positionOffset = $positionOffset, positionOffsetPixels = $positionOffsetPixels"
                    )
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d(TAG, "onPageSelected() called with: position = $position")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    Log.d(TAG, "onPageScrollStateChanged() called with: state = $state")
                }
            })
        }

        val buttonDrag = findViewById<MaterialButton>(R.id.buttonDrag)
        buttonDrag.setOnClickListener {
            // 程序拖拽
            fakeDragBy()
        }

        val switchMaterialEnableManual =
            findViewById<SwitchMaterial>(R.id.switchMaterialEnableManual)
        switchMaterialEnableManual.setOnCheckedChangeListener { _, isChecked ->
            // 是否允许手动滑动
            viewPager2.isUserInputEnabled = isChecked
        }

        val switchVerticalManual = findViewById<SwitchMaterial>(R.id.switchVerticalManual)
        switchVerticalManual.setOnCheckedChangeListener { _, isChecked ->
            // 水平垂直滑动
            viewPager2.orientation =
                if (isChecked) ViewPager2.ORIENTATION_VERTICAL else ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun fakeDragBy() {
        viewPager2.beginFakeDrag()
        if (viewPager2.fakeDragBy(-310f)) {
            viewPager2.endFakeDrag()
        }
    }
}