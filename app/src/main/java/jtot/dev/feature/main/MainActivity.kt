package jtot.dev.feature.main

import android.content.Intent
import android.os.Bundle
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityMainBinding
import jtot.dev.feature.play.PlayActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.fabPlay.setOnClickListener {
            Intent(this, PlayActivity::class.java).apply {
            }.run(::startActivity)
        }
    }
}
