package jtot.dev.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import jtot.dev.R
import jtot.dev.base.BaseFragment
import jtot.dev.databinding.FragmentMainBinding
import jtot.dev.model.Folder
import jtot.dev.utils.showSnackBar

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val todosAdapter = TodosAdapter()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTodos.adapter = todosAdapter

        mainViewModel.folderLiveData.observe(viewLifecycleOwner) { folder ->
            binding.clContainer.visibility = if (folder == null) View.VISIBLE else View.GONE
            binding.rvTodos.visibility = if (folder == null) View.GONE else View.VISIBLE
            folder?.let { todosAdapter.setTodosList(listOf(it)) }
        }

        mainViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showSnackBar(view, requireActivity(), message)
        }

        createDummyData()
        mainViewModel.readFolder()
    }

    private fun createDummyData() {
        mainViewModel.writeFolder(Folder("work", listOf()).createDummy())
    }
}
