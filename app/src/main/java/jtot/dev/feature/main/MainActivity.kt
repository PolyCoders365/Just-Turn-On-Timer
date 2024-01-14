package jtot.dev.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.sidesheet.SideSheetBehavior
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityMainBinding
import jtot.dev.feature.play.PlayActivity
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.model.Folder
import jtot.dev.utils.dpToPixel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val standardSideSheetBehavior: SideSheetBehavior<View> by lazy {
        SideSheetBehavior.from(binding.sideSheet)
    }

    private val folderAdapter: FolderAdapter by lazy {
        FolderAdapter().apply {
            setFolderList(
                listOf(
                    Folder().createDummyFolders(),
                    Folder().createDummyFolders(),
                    Folder().createDummyFolders(),
                ),
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvFolder.run {
            addItemDecoration(ContentDecoration(dpToPixel(16f).toInt()))
            adapter = folderAdapter
        }

        binding.babAppbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_main_today -> {
                    standardSideSheetBehavior.expand()
                    true
                }

                else -> false
            }
        }

        binding.btnClose.setOnClickListener {
            standardSideSheetBehavior.hide()
        }

        binding.fabPlay.setOnClickListener {
            Intent(this, PlayActivity::class.java).apply {
            }.run(::startActivity)
        }
    }
}
