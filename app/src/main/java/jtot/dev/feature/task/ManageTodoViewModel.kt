package jtot.dev.feature.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jtot.dev.base.BaseViewModel
import jtot.dev.model.Folder
import jtot.dev.model.Schedule
import jtot.dev.model.Todo

class ManageTodoViewModel : BaseViewModel() {
    private var _todoList =
        MutableLiveData<List<Any>>().apply {
            value =
                listOf(
                    Todo("Todo 1").createStarDummy(),
                )
        }
    val todoList: LiveData<List<Any>> = _todoList
    private var _searchResults = MutableLiveData<List<Any>>()
    val searchResults: LiveData<List<Any>> = _searchResults
    private var tempSchedule = Schedule()
    private var tempTodo = Todo()

    fun setTempSchedule(schedule: Schedule) {
        tempSchedule = schedule
    }

    fun getTempSchedule() = tempSchedule

    fun setTempTodo(todo: Todo) {
        tempTodo = todo
    }

    fun getTempTodo() = tempTodo

    fun addTodo(todo: Todo) {
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        currentList.add(todo) // 새로운 Todo 객체를 추가합니다.
        _todoList.value = currentList
    }

    fun search(query: String) {
        // 전체 데이터에서 query를 포함한 데이터를 가져오고
        val folder = Folder()
        val findList = folder.findTodo(query)
        // findList를 searhList에 업데이트

//        val results = _todoList.value?.filter { it is Todo && (it as Todo).title.contains(query) } ?: listOf()
//        _searchResults.value = results
    }
}
