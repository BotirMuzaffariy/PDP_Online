package uz.lycr.task2pdponline.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.R
import uz.lycr.task2pdponline.databinding.ItemMainRvBinding
import uz.lycr.task2pdponline.db.AppDb
import uz.lycr.task2pdponline.models.Course
import uz.lycr.task2pdponline.models.Module

class MainRvAdapter(var list: List<Course>, var listener: MainRvOnClickListener) :
    RecyclerView.Adapter<MainRvAdapter.MyVh>() {

    inner class MyVh(var itemBinding: ItemMainRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(course: Course) {
            itemBinding.tvName.isSelected = true
            itemBinding.tvName.text = course.name

            itemBinding.rv.adapter = MainRvInnerAdapter(
                AppDb.getInstance(itemBinding.root.context).moduleDao().getAllModules()
                    .filter { it.courseId == course.id },
                object : MainRvInnerAdapter.MainInnerRvClickListener {
                    override fun onItemClick(moduleId: Int) {
                        val bundle = Bundle()
                        bundle.putInt("moduleId", moduleId)
                        Navigation.findNavController(itemBinding.root).navigate(R.id.allLessonsFragment, bundle)
                    }
                })

            itemBinding.tvAll.setOnClickListener { listener.onTvAllClick(course.id) }
        }
    }

    interface MainRvOnClickListener {
        fun onTvAllClick(courseId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(ItemMainRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

}