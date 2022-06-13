package pl.edu.pja.mp1.adapter

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.mp1.databinding.ShortTaskViewBinding
import pl.edu.pja.mp1.db.AppDatabase
import pl.edu.pja.mp1.model.Task
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.concurrent.thread


class TaskAdapter(private val database: AppDatabase,  val onDeleted2: ()->Unit) : RecyclerView.Adapter<TaskViewHolder>() {
      private var tasks = mutableListOf<Task>()
      private val handler = HandlerCompat.createAsync(Looper.getMainLooper())
      private var currentDateOld = LocalDate.now()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ShortTaskViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding, onDeleted = {
            reload() {}
        }, onDeleted2 = {
            onDeleted2()
        })
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    fun reload(onComplete: () -> Unit) = thread {

        val currentDateNew = LocalDate.now()
        val currentWeekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)

        val dayDifference = ChronoUnit.DAYS.between(currentDateNew, currentDateOld).toInt()

        if ( dayDifference > 0) {
            database.tasks.updateDaysToDeadline(dayDifference)
            database.tasks.deleteOldTasks()
        }
        currentDateOld = currentDateNew

        val data = database.tasks.getAllSortedAndInWeek(currentWeekOfYear).map { it.toTaskModel() }
        notifyChanges(data)
        onComplete()
    }

    fun getTasksLeft() : Int{
        return tasks.size
    }

    private fun notifyChanges(newData: List<Task>) {
        val callback = TaskDiffCallback(tasks, newData)
        val diffs = DiffUtil.calculateDiff(callback)
        tasks = newData.toMutableList()
        handler.post {
            diffs.dispatchUpdatesTo(this)
        }
    }
}