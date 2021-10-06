package uz.lycr.task2pdponline.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Module::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("module_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var about: String,
    var position: Int,
    var image: String,
    @ColumnInfo(name = "module_id")
    var moduleId: Int,
    @ColumnInfo(name = "course_id")
    var courseId: Int
)
