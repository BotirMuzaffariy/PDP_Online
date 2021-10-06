package uz.lycr.task2pdponline.dao

import androidx.room.*
import uz.lycr.task2pdponline.models.Course

@Dao
interface CourseDao {

    @Insert
    fun addCourse(course: Course)

    @Update
    fun updateCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)

    @Query("select * from course")
    fun getAllCourses(): List<Course>

    @Query("select * from course where id=:id")
    fun getCourseById(id: Int): Course

    @Query("select id from course where name=:name")
    fun getIdByCourse(name: String): Int

}