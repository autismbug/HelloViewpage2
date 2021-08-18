package com.autismbug.hello.viewpage2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.autismbug.hello.viewpage2.fragment.TestFragment

class FragmentPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    private val colors = arrayOf("#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC")

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_MESSAGE -> TestFragment.newInstance("消息$position", colors[0])
            PAGE_CONTACT -> TestFragment.newInstance("通讯录$position", colors[1])
            PAGE_SETTING -> TestFragment.newInstance("设置$position", colors[2])
            PAGE_MINE -> TestFragment.newInstance("我$position", colors[3])
            else -> TestFragment.newInstance("$position", colors[0])
        }
    }

    companion object {
        const val PAGE_MESSAGE = 0
        const val PAGE_CONTACT = 1
        const val PAGE_SETTING = 2
        const val PAGE_MINE = 3
    }

}