package br.com.necctares.myanimetowatch.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.necctares.myanimetowatch.databinding.ActivityAddTaskBinding
import br.com.necctares.myanimetowatch.datasource.TaskDataSource
import br.com.necctares.myanimetowatch.extensions.format
import br.com.necctares.myanimetowatch.extensions.text
import br.com.necctares.myanimetowatch.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){
            val intExtra = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(intExtra)?.let{
                binding.issueTitle.text = it.name
                binding.startDate.text = it.date
                binding.numberOfEps.text = it.numberOfEps.toString()
            }
        }

        insertListeners()
    }

    private fun insertListeners(){
        binding.startDate.editText?.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.startDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
        binding.confirmButton.setOnClickListener {
            val newTask = Task(
                name = binding.issueTitle.text,
                date = binding.startDate.text,
                numberOfEps = binding.numberOfEps.text.toInt(),
                taskId = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(newTask)
            setResult(Activity.RESULT_OK)
            finish()
        }
        binding.cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    companion object{
        const val TASK_ID = "task_id"
    }
}