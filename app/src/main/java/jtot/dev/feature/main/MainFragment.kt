package jtot.dev.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import jtot.dev.R
import jtot.dev.base.BaseFragment
import jtot.dev.databinding.FragmentMainBinding
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
            if (folder != null) {
                binding.clContainer.visibility = View.GONE
                binding.rvTodos.visibility = View.VISIBLE
                val folders = listOf(folder)
                todosAdapter.setTodosList(folders)
            } else {
                binding.clContainer.visibility = View.VISIBLE
                binding.rvTodos.visibility = View.GONE
            }
        }
        mainViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showSnackBar(view, requireActivity(), message)
        }

        mainViewModel.writeFolder()
        mainViewModel.readFolder()
    }
}
