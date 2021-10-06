package uz.lycr.task2pdponline.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.lycr.task2pdponline.dao.CourseDao
import uz.lycr.task2pdponline.dao.LessonDao
import uz.lycr.task2pdponline.dao.ModuleDao
import uz.lycr.task2pdponline.models.Course
import uz.lycr.task2pdponline.models.Lesson
import uz.lycr.task2pdponline.models.Module

@Database(entities = [Course::class, Lesson::class, Module::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun moduleDao(): ModuleDao
    abstract fun lessonDao(): LessonDao

    companion object {
        private var instanse: AppDb? = null

        @Synchronized
        fun getInstance(context: Context): AppDb {
            if (instanse == null) {
                instanse = Room.databaseBuilder(context, AppDb::class.java, "pdp_online_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instanse!!
        }
    }

}