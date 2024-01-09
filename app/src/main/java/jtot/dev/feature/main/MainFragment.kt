package jtot.dev.feature.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import jtot.dev.R
import jtot.dev.base.BaseFragment
import jtot.dev.databinding.FragmentMainBinding
import jtot.dev.db.FolderDao
import jtot.dev.db.FolderDataDB
import jtot.dev.model.Folder
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private lateinit var folderDao: FolderDao
    private val todosAdapter: TodosAdapter by lazy { TodosAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        folderDao = FolderDataDB.getDatabase(requireContext()).folderDao()

        binding.rvTodos.adapter = todosAdapter

        lifecycleScope.launch {
            val dummyFolder = Folder(0, "Work", listOf()).createDummy()
            folderDao.insert(dummyFolder)

            val folders = folderDao.getAllFolders()
            if (folders.isEmpty()) {
                binding.clContainer.visibility = View.VISIBLE
            } else {
                binding.clContainer.visibility = View.GONE
                todosAdapter.setTodosList(folders)
            }
        }
    }
}
