package pl.edu.pja.mp1.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.edu.pja.mp1.model.Task

@Dao
interface TaskDAO {
    @Insert
    fun insert(taskDTO: TaskDTO)

    @Query("SELECT * FROM taskDTO WHERE id = :taskId")
    fun getOne(taskId: Long) : TaskDTO

    @Query("DELETE FROM taskDTO WHERE id = :taskId")
    fun delete(taskId: Long)

    @Query("SELECT * FROM taskDTO WHERE week >= :currentWeek AND progress < 100 ORDER BY daysToDeadline")
    fun getAllSortedAndInWeek(currentWeek : Int): List<TaskDTO>

    @Query("SELECT * FROM taskDTO WHERE week = :currentWeek AND progress < 100 ORDER BY daysToDeadline")
    fun getAllSortedAndInThisWeek(currentWeek : Int): List<TaskDTO>

    @Query("UPDATE taskDTO SET daysToDeadline = daysToDeadline - :dayDiff")
    fun updateDaysToDeadline(dayDiff: Int)

    @Query("DELETE FROM taskDTO WHERE daysToDeadline < 0")
    fun deleteOldTasks()

    @Query("SELECT * FROM taskDTO")
    fun getAll() : List<TaskDTO>

    @Query("UPDATE taskDTO SET name = :name, priority = :priority, deadline = :deadline, week = :week, daysToDeadline = :daysToDeadline, progress = :progress, descriptor = :descriptor WHERE id = :id")
    fun updateOne(id : Long, name : String, priority : Int, deadline: String, week : Int, daysToDeadline : Int, progress : Int, descriptor : String)

    @Query("UPDATE taskDTO SET progress = :progress WHERE id= :id")
    fun updateOneProgress(id: Long, progress: Int)
}