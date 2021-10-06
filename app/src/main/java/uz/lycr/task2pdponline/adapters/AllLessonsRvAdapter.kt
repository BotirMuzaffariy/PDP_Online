package uz.lycr.task2pdponline.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.databinding.ItemAllLessonsRvBinding
import uz.lycr.task2pdponline.models.Lesson

class AllLessonsRvAdapter(var list: List<Lesson>, var listener: AllLessonsOnClickListener) :
    RecyclerView.Adapter<AllLessonsRvAdapter.AllLessonsVh>() {

    inner class AllLessonsVh(var itemBiding: ItemAllLessonsRvBinding) :
        RecyclerView.ViewHolder(itemBiding.root) {
        fun onBind(lesson: Lesson) {
            itemBiding.tvPosition.text = "${lesson.position}\ndars"
            itemBiding.sv.setOnClickListener { listener.onItemClick(lesson.id) }
        }
    }

    interface AllLessonsOnClickListener {
        fun onItemClick(lessonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllLessonsVh {
        return AllLessonsVh(ItemAllLessonsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AllLessonsVh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

}