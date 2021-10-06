package uz.lycr.task2pdponline.dao

import androidx.room.*
import uz.lycr.task2pdponline.models.Course
import uz.lycr.task2pdponline.models.Module

@Dao
interface ModuleDao {

    @Insert
    fun addModule(module: Module)

    @Update
    fun updateModule(module: Module)

    @Delete
    fun deleteModule(module: Module)

    @Query("select * from module order by position")
    fun getAllModules(): List<Module>

    @Query("select * from module where id=:id")
    fun getModuleById(id: Int): Module

    @Query("select id from module where name=:name and position=:position and course_id=:courseId")
    fun getIdByModule(name: String, position: Int, courseId: Int): Int

}