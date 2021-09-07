package br.com.necctares.myanimetowatch.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.necctares.myanimetowatch.databinding.ActivityMainBinding
import br.com.necctares.myanimetowatch.datasource.TaskDataSource
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter = adapter
        updateList()

        insertListeners()
    }

    private fun insertListeners() {
        binding.newAnime.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        binding.closeApp.setOnClickListener {
            exitProcess(0)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.taskId)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }

        adapter.listenerWatched = {
            TaskDataSource.watched(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        binding.emptyScreen.emptyState.visibility = if (list.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 200
    }
}