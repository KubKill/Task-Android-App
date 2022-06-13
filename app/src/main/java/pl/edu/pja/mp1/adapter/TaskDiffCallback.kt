package pl.edu.pja.mp1.adapter

import androidx.recyclerview.widget.DiffUtil
import pl.edu.pja.mp1.model.Task

class TaskDiffCallback(private val before: List<Task>, private val after: List<Task>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = before.size
    override fun getNewListSize(): Int = after.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        before[oldItemPosition].id == after[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        before[oldItemPosition].name == after[newItemPosition].name &&
                before[oldItemPosition].priority == after[newItemPosition].priority &&
                before[oldItemPosition].deadline == after[newItemPosition].deadline &&
                before[oldItemPosition].week == after[newItemPosition].week &&
                before[oldItemPosition].daysToDeadline == after[newItemPosition].daysToDeadline &&
                before[oldItemPosition].progress == after[newItemPosition].progress &&
                before[oldItemPosition].descriptor == after[newItemPosition].descriptor
}