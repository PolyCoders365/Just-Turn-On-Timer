package jtot.dev.feature.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivitySettingBinding

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.layoutAlarm.setOnClickListener {
            Intent().apply {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, this@SettingActivity.packageName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }.run(::startActivity)
        }
    }
}
