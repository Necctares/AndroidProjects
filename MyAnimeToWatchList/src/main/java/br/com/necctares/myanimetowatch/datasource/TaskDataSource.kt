package br.com.necctares.myanimetowatch.datasource

import br.com.necctares.myanimetowatch.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        if (task.taskId == 0) {
            list.add(task.copy(taskId = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun findById(intExtra: Int) = list.find { it.taskId == intExtra }
    fun deleteTask(it: Task) {
        list.remove(it)
    }

    fun watched(it: Task) {
        list.remove(it)
        list.add(it.copy(actualEp = it.actualEp + 1))
    }
}