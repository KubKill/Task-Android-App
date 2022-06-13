package pl.edu.pja.mp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.pja.mp1.adapter.TaskAdapter
import pl.edu.pja.mp1.databinding.ActivityMainBinding
import pl.edu.pja.mp1.db.AppDatabase

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val taskAdapter by lazy { TaskAdapter(AppDatabase.open(this)) {setupTasksLeftInfo()} }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTaskList()
        setupTasksLeftInfo()
        setContentView(binding.root)
        setupAddNewButton()
    }

    override fun onResume() {
        super.onResume()
        taskAdapter.reload() {
            runOnUiThread {
                setupTasksLeftInfo()
            }
        }
        // setup nie dizała bo reload jest w nowym wątku i wolniej aktualizuje dane
    }

    private fun setupTaskList() {
        taskAdapter.reload(){}
        binding.listTask.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupAddNewButton() {
        binding.buttonNew.setOnClickListener{
            val intent = Intent(this, CreateEditActivity::class.java)
            intent.putExtra("mode", "CREATE")
            startActivity(intent)
        }
    }

    private fun setupTasksLeftInfo() {
        binding.textTasksLeft.text = taskAdapter.getTasksLeft().toString()
    }
}