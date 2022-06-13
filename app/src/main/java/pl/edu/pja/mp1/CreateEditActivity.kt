package pl.edu.pja.mp1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.pja.mp1.adapter.TaskAdapter
import pl.edu.pja.mp1.databinding.ActivityCreateEdit2Binding
import pl.edu.pja.mp1.db.AppDatabase
import pl.edu.pja.mp1.db.TaskDTO
import pl.edu.pja.mp1.model.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.concurrent.thread

class CreateEditActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.open(this)}
    private val binding by lazy { ActivityCreateEdit2Binding.inflate(layoutInflater)}
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var mode = "INIT"
    private var taskID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMode()
        setTaskID()
        setContentView(binding.root)
        if (mode.equals("EDIT")){
            setEditView()
        }
        setupReturnButton()
        setupAddButton()
    }

    private fun setupReturnButton() {
        binding.buttonReturn.setOnClickListener{
            if (mode.equals("EDIT")) {
                val intent = Intent(this, PreviewActivity::class.java)
                intent.putExtra("id", taskID.toLong())
                startActivity(intent)
                finish()
            }
            finish()
        }
    }

    private fun setupAddButton() {
        binding.buttonEditCreate.setOnClickListener{
            if (mode.equals("CREATE")) {
                addNewTask()
            }
            if (mode.equals("EDIT")) {
                updateTask()
            }
            finish()

        }
    }

    private fun setMode() {
        mode = intent?.getStringExtra("mode").toString()
    }

    private fun setTaskID() {
        taskID = intent.getIntExtra("id", 0)
    }

    private fun setEditView() {
        thread {
            val task : Task = db.tasks.getOne(taskID.toLong()).toTaskModel()

            this.runOnUiThread {

                binding.textInputTitle.setText(task.name)
                binding.textInputPriority.setText(task.priority.toString())
                binding.textInputDeadline.setText(task.deadline)
                binding.textInputDescription.setText(task.descriptor)

                binding.buttonEditCreate.text = "Zatwierdź"
                binding.textTitle.text = "Edytuj dane"
            }
        }
    }

    private fun addNewTask(){
        val current = LocalDate.now()

        val tasksDate = LocalDate.parse(binding.textInputDeadline.text.toString(), formatter)
        val dayDifference = ChronoUnit.DAYS.between(current, tasksDate).toInt()

        val dateString = binding.textInputDeadline.text.toString().split("-")
        for (s in dateString)
            s.replaceFirst("^0+(?!$)", "")

        val calendarTask = Calendar.getInstance()

        calendarTask.set(dateString[0].toInt(), dateString[1].toInt() - 1, dateString[2].toInt())

        val task = TaskDTO(
            0,
            binding.textInputTitle.text.toString(),
            binding.textInputPriority.text.toString().toInt(),
            binding.textInputDeadline.text.toString(),
            calendarTask.get(Calendar.WEEK_OF_YEAR),
            dayDifference,
            0,
            binding.textInputDescription.text.toString()
        )

        thread {
            db.tasks.insert(task)
            finish()
        }
    }

    private fun updateTask() {
        val current = LocalDate.now()

        val tasksDate = LocalDate.parse(binding.textInputDeadline.text.toString(), formatter)
        val dayDifference = ChronoUnit.DAYS.between(current, tasksDate).toInt()

        val dateString = binding.textInputDeadline.text.toString().split("-")
        for (s in dateString)
            s.replaceFirst("^0+(?!$)", "")

        val calendarTask = Calendar.getInstance()

        calendarTask.set(dateString[0].toInt(), dateString[1].toInt() - 1, dateString[2].toInt())

        val progress = intent.getIntExtra("progress", -0)

        thread {
            db.tasks.updateOne(
                taskID.toLong(),
                binding.textInputTitle.text.toString(),
                binding.textInputPriority.text.toString().toInt(),
                binding.textInputDeadline.text.toString(),
                calendarTask.get(Calendar.WEEK_OF_YEAR),
                dayDifference,
                progress,
                binding.textInputDescription.text.toString())
        }
    }
}