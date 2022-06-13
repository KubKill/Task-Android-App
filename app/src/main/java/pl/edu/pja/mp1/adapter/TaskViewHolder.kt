package pl.edu.pja.mp1.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pl.edu.pja.mp1.CreateEditActivity
import pl.edu.pja.mp1.MainActivity
import pl.edu.pja.mp1.PreviewActivity
import pl.edu.pja.mp1.databinding.ShortTaskViewBinding
import pl.edu.pja.mp1.db.AppDatabase
import pl.edu.pja.mp1.model.Task
import kotlin.concurrent.thread

class TaskViewHolder(private val layoutBinding: ShortTaskViewBinding, val onDeleted: ()->Unit, val onDeleted2: ()->Unit ) : RecyclerView.ViewHolder(layoutBinding.root) {

    private val db by lazy { AppDatabase.open(layoutBinding.root.context)}
    private val context = layoutBinding.root.context

    fun bind(task : Task) = with(layoutBinding) {
        textTitle.text = task.name
        textInputPriority.text = task.priority.toString()
        textDate.text = task.deadline.toString()
        progressBarProgress.progress = task.progress.toInt()

        layoutBinding.root.setOnClickListener{
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra("id", task.id)
            context.startActivity(intent)
        }

        layoutBinding.root.setOnLongClickListener {
            MaterialAlertDialogBuilder(context)
                .setMessage("Czy na pewno chcesz zakończyc zadanie?")
                .setNegativeButton("Nie", DialogInterface.OnClickListener { dialog, id ->
                })
                .setPositiveButton("Tak", DialogInterface.OnClickListener(){ dialog, id ->
                    thread{
                        db.tasks.delete(task.id)
                        onDeleted()
                        //onDeleted2()
                    }
                })
                .show()
            true
        }
    }
}