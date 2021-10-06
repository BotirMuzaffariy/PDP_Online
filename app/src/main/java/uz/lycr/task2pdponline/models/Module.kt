package uz.lycr.task2pdponline.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("course_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Module(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var position: Int,
    var image: String,
    @ColumnInfo(name = "course_id")
    var courseId: Int
)
