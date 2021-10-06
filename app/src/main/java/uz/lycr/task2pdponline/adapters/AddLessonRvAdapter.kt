package uz.lycr.task2pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.databinding.ItemAddLessonRvBinding
import uz.lycr.task2pdponline.databinding.ItemAllLessonsRvBinding
import uz.lycr.task2pdponline.models.Lesson
import java.io.File

class AddLessonRvAdapter(var list: List<Lesson>, var listener: AddLessonOnClickListener) : RecyclerView.Adapter<AddLessonRvAdapter.AddLessonVh>() {

    inner class AddLessonVh(var itemBinding: ItemAddLessonRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(lesson: Lesson, position: Int) {
            itemBinding.tvPosition.isSelected = true
            itemBinding.tvName.isSelected = true

            itemBinding.tvName.text = lesson.name
            itemBinding.tvPosition.text = "${lesson.position}-dars"
            if (lesson.image != "") itemBinding.ivImage.setImageURI(Uri.fromFile(File(lesson.image)))

            itemBinding.cvEdit.setOnClickListener { listener.onBtnEditClick(lesson.id) }
            itemBinding.cvDelete.setOnClickListener { listener.onBtnDeleteClick(lesson, position) }
        }
    }

    interface AddLessonOnClickListener {
        fun onBtnEditClick(lessonId: Int)
        fun onBtnDeleteClick(lesson: Lesson, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddLessonVh {
        return AddLessonVh(
            ItemAddLessonRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddLessonVh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

}