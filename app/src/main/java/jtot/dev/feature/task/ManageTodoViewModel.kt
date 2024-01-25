package jtot.dev.feature.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jtot.dev.base.BaseViewModel
import jtot.dev.model.Todo

class ManageTodoViewModel : BaseViewModel() {
    private var _todoList =
        MutableLiveData<List<Any>>().apply {
            value =
                listOf(
                    Todo("Todo 1"),
                )
        }
    val todoList: LiveData<List<Any>> = _todoList
    private var _searchResults = MutableLiveData<List<Any>>()
    val searchResults: LiveData<List<Any>> = _searchResults

    fun addTodo(todo: Todo) {
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        currentList.add(todo) // 새로운 Todo 객체를 추가합니다.
        _todoList.value = currentList
    }

    fun search(query: String) {
        val results = _todoList.value?.filter { it is Todo && (it as Todo).title.contains(query) } ?: listOf()
        _searchResults.value = results
    }
}
