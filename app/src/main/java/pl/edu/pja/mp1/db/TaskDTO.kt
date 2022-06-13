package pl.edu.pja.mp1.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.edu.pja.mp1.model.Task

@Entity
data class TaskDTO (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    var priority: Int,
    var deadline: String,
    var week: Int,
    var daysToDeadline: Int,
    var progress: Int,
    var descriptor: String
) {
    fun toTaskModel(): Task {
        return Task(id, name, priority, deadline, week, daysToDeadline, progress, descriptor)
    }
}