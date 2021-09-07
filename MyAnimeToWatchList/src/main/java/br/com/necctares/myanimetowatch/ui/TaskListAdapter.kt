package br.com.necctares.myanimetowatch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.necctares.myanimetowatch.R
import br.com.necctares.myanimetowatch.databinding.ItemTaskBinding
import br.com.necctares.myanimetowatch.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}
    var listenerWatched: (Task) -> Unit = {}

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.animeTitle.text = item.name
            binding.animeInfo.text =
                "Proximo Episodio: ${showEpisode(item)}\t\t\tData de inicio: ${item.date}"
            binding.moreOptions.setOnClickListener {
                showPopUp(item)
            }
        }

        private fun showEpisode(item: Task): String {
            if (item.numberOfEps < item.actualEp) {
                return "Anime Concluído"
            }
            return item.actualEp.toString()
        }

        private fun showPopUp(item: Task) {
            val moreOpt = binding.moreOptions
            val popUpMenu = PopupMenu(moreOpt.context, moreOpt)
            popUpMenu.menuInflater.inflate(R.menu.popupmenu, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete_action -> listenerDelete(item)
                    R.id.watched_action -> {
                        if (item.actualEp > item.numberOfEps) {
                            Toast.makeText(
                                moreOpt.context,
                                "Você já assistiu todos os episodios!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            listenerWatched(item)
                        }
                    }
                    R.id.edit_action -> listenerEdit(item)
                }
                return@setOnMenuItemClickListener true
            }
            popUpMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem === newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.taskId == newItem.taskId
}