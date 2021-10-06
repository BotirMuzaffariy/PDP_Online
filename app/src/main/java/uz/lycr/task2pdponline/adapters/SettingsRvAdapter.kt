package uz.lycr.task2pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.databinding.ItemSettingsRvBinding
import uz.lycr.task2pdponline.models.Course
import java.io.File

class SettingsRvAdapter(var list: List<Course>, var listener: SettingsOnClickListener) :
    RecyclerView.Adapter<SettingsRvAdapter.SettingsVh>() {

    inner class SettingsVh(var itemBinding: ItemSettingsRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(course: Course, position: Int) {
            itemBinding.tvName.isSelected = true
            itemBinding.tvName.text = course.name
            if (course.image != "") itemBinding.ivImage.setImageURI(Uri.fromFile(File(course.image)))

            itemBinding.cv.setOnClickListener { listener.onItemClick(course.id) }
            itemBinding.cvEdit.setOnClickListener { listener.onBtnEditClick(course.id) }
            itemBinding.cvDelete.setOnClickListener { listener.onBtnDeleteClick(course, position) }
        }
    }

    interface SettingsOnClickListener {
        fun onItemClick(courseId: Int)
        fun onBtnEditClick(courseId: Int)
        fun onBtnDeleteClick(course: Course, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsVh {
        return SettingsVh(ItemSettingsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SettingsVh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

}