package pl.edu.pja.mp1.model

import android.graphics.BitmapFactory
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.time.LocalDate


data class Task(
    var id: Long,
    var name: String,
    var priority: Int,
    var deadline: String,
    var week: Int,
    var daysToDeadline: Int,
    var progress: Int,
    var descriptor: String
)