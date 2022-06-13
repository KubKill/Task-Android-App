package pl.edu.pja.mp1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.pja.mp1.databinding.ActivityCreateEdit2Binding.inflate
import pl.edu.pja.mp1.databinding.ActivityMainBinding
import pl.edu.pja.mp1.databinding.ActivityPreviewBinding
import pl.edu.pja.mp1.db.AppDatabase
import pl.edu.pja.mp1.db.TaskDTO
import pl.edu.pja.mp1.fragment.PieDiagramFragment
import pl.edu.pja.mp1.model.Task
import kotlin.concurrent.thread

class PreviewActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.open(this)}
    private val binding by lazy { ActivityPreviewBinding.inflate(layoutInflater) }
    private val pieDiagramFragment
        get() = supportFragmentManager.findFragmentByTag("PieDiagram") as PieDiagramFragment
    private var taskID = 0
    private val taskId by lazy { intent.getLongExtra("id", -0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setData() {
            setPieDiagram(binding.textInputProgress.text.toString().toInt())
        }
        setEditButton()
        setReturnButton()
        setProgressSetButton()
    }

    private fun setData( onDataGot: ()->Unit) {
        val intent: Intent = intent
        var task: Task
        thread {
            task = db.tasks.getOne(intent.getLongExtra("id", -0)).toTaskModel()

            taskID = task.id.toInt()

            this.runOnUiThread {
                binding.textTitle.text = task.name
                binding.textInputDeadline.text = task.deadline
                binding.textInputPriority.text = task.priority.toString()
                binding.textDesc.text = task.descriptor
                binding.textInputProgress.setText(task.progress.toString())

                onDataGot()
            }
        }
    }

    private fun setEditButton() {
        binding.buttonEditCreate.setOnClickListener{
            val intent = Intent(this, CreateEditActivity::class.java)
            intent.putExtra("mode", "EDIT")
            intent.putExtra("id", taskID)
            intent.putExtra("progress", binding.textInputProgress.text.toString().toInt())
            startActivity(intent)
            finish()
        }
    }

    private fun setReturnButton() {
        binding.buttonReturn.setOnClickListener{
            finish()
        }
    }

    private fun setProgressSetButton() {
        binding.buttonProgressSet.setOnClickListener {
            thread {
                db.tasks.updateOneProgress(taskId, binding.textInputProgress.text.toString().toInt())
            }
            pieDiagramFragment.drawPieDiagram(binding.textInputProgress.text.toString().toInt())
        }
    }

    private fun setPieDiagram(progress: Int) {
        pieDiagramFragment.drawPieDiagram(progress)
    }
}