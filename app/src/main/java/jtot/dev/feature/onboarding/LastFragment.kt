package jtot.dev.feature.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import jtot.dev.R
import jtot.dev.base.BaseFragment
import jtot.dev.databinding.FragmentLastBinding
import jtot.dev.feature.main.MainActivity

class LastFragment : BaseFragment<FragmentLastBinding>(
    R.layout.fragment_last,
) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        binding.btnStart.setOnClickListener {
            Intent(requireContext(), MainActivity::class.java).apply {
            }.run(::startActivity)
        }
    }
}
