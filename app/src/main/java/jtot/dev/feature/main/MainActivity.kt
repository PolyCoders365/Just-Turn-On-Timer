package jtot.dev.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.sidesheet.SideSheetBehavior
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityMainBinding
import jtot.dev.feature.play.PlayActivity
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.model.Folder
import jtot.dev.utils.dpToPixel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()
    private val standardSideSheetBehavior: SideSheetBehavior<View> by lazy {
        SideSheetBehavior.from(binding.sideSheet)
    }

    private val folderAdapter: FolderAdapter by lazy {
        FolderAdapter(
            createFolder = { folder ->
                viewModel.createFolder(folder)
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setFolderList(
            listOf(
                Folder().createDummyFolders("1"),
                Folder().createDummyFolders("2"),
                Folder().createDummyFolders("3"),
            ),
        )
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

        binding.fabPlay.setOnClickListener {
            Intent(this, PlayActivity::class.java).apply {
            }.run(::startActivity)
        }

        binding.btnNewFolder.setOnClickListener {
            folderAdapter.createNewFolder()
        }
        binding.btnClose.setOnClickListener {
            standardSideSheetBehavior.hide()
        }
        viewModel.folderLiveData.observe(this) { folder ->
            folderAdapter.setFolderList(folder.docs)
        }
    }
}
