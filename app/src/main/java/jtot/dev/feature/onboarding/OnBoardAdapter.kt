package jtot.dev.feature.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        // Return the instance of your fragment based on the position
        return when (position) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> LastFragment()
            else -> {
                FirstFragment()
            }
        }
    }

    override fun getItemCount(): Int = 3
}
