package uz.lycr.task2pdponline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.adapters.AddLessonRvAdapter
import uz.lycr.task2pdponline.databinding.FragmentAddLessonBinding
import uz.lycr.task2pdponline.databinding.FragmentEditLessonBinding
import uz.lycr.task2pdponline.db.AppDb

class EditLessonFragment : Fragment() {

    private var lessonId: Int = -1

    private lateinit var db: AppDb
    private lateinit var binding: FragmentEditLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lessonId = it.getInt("lessonId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        val lesson = db.lessonDao().getLessonById(lessonId)
        binding = FragmentEditLessonBinding.inflate(inflater, container, false)

        binding.etName.setText(lesson.name)
        binding.etAbout.setText(lesson.about)
        binding.tvTop.text = "${lesson.position}-dars"
        binding.etPosition.setText(lesson.position.toString())

        binding.svSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val about = binding.etAbout.text.toString()
            val position = binding.etPosition.text.toString()

            if (name.isNotEmpty() && about.isNotEmpty() && position.isNotEmpty()) {
                if (db.lessonDao().getAllLessons().filter { it.name.equals(name, ignoreCase = true) && it.position == position.toInt() }.count() == 0) {
                    lesson.name = name
                    lesson.about = about
                    lesson.position = position.toInt()
                    db.lessonDao().updateLesson(lesson)
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Bu dars allaqachon qo`shilgan !!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Ma'lumotlarni to`liq kiriting", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}