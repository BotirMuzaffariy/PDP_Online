package uz.lycr.task2pdponline.dao

import androidx.room.*
import uz.lycr.task2pdponline.models.Course
import uz.lycr.task2pdponline.models.Lesson

@Dao
interface LessonDao {

    @Insert
    fun addLesson(lesson: Lesson)

    @Update
    fun updateLesson(lesson: Lesson)

    @Delete
    fun deleteLesson(lesson: Lesson)

    @Query("select * from lesson order by position")
    fun getAllLessons(): List<Lesson>

    @Query("select * from lesson where id=:id")
    fun getLessonById(id: Int): Lesson

    @Query("select id from lesson where name=:name and position=:position and module_id=:moduleId and course_id=:courseId")
    fun getIdByLesson(name: String, position: Int, moduleId: Int, courseId: Int): Int

}