package jtot.dev.feature.onboarding

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(R.layout.activity_on_boarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewpager.adapter = OnBoardAdapter(this)
        TabLayoutMediator(binding.tlMain, binding.viewpager) { tab, position ->
            // remove ripple effect
            tab.view.background = null
        }.attach()
    }
}
