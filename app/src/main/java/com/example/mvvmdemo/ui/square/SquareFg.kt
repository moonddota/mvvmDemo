package com.example.mvvmdemo.ui.square

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseFragment
import com.example.mvvmdemo.databinding.SquareFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class SquareFg() : BaseFragment<SquareFragmentBinding>() {

    override fun immersionBar(): Boolean = true

    override fun initView() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> SystemFg(SystemFg.SYSTEM_TAG)
                    1 -> SystemFg(SystemFg.NAVIGATION_TAG)
                    else -> SystemFg(SystemFg.SYSTEM_TAG)
                }
            }
        }
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()
    }

    override fun initData() {

    }

    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.view_home_tab, null)
        val text = view.findViewById<AppCompatTextView>(R.id.tv_tab)
        text.text = when (position) {
            0 -> "体系"
            1 -> "导航"
            else -> "体系"
        }
//        text.setCompoundDrawables(null, getTabIcon(position), null, null)
        return view
    }

}