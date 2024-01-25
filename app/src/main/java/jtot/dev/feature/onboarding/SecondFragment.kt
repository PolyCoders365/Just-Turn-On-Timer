package jtot.dev.feature.onboarding

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import jtot.dev.R
import jtot.dev.base.BaseFragment
import jtot.dev.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>(
    R.layout.fragment_second,
) {
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 210
        val requestPermissionList =
            arrayOf(
                android.Manifest.permission.POST_NOTIFICATIONS,
            )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        binding.btnAlram.setOnClickListener {
            permissionCheck()
        }
    }

    private fun permissionCheck() {
        val isAllPermissionGranted =
            requestPermissionList.all { permission ->
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission,
                ) == PackageManager.PERMISSION_GRANTED
            }
        if (!isAllPermissionGranted) {
            requestOpen()
        } else {
        }
    }

    private fun requestOpen() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            requestPermissionList,
            REQUEST_CODE_PERMISSIONS,
        )
    }
}
