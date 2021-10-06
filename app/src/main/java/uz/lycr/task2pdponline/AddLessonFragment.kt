package uz.lycr.task2pdponline

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.adapters.AddLessonRvAdapter
import uz.lycr.task2pdponline.databinding.FragmentAddLessonBinding
import uz.lycr.task2pdponline.db.AppDb
import uz.lycr.task2pdponline.models.Lesson
import uz.lycr.task2pdponline.models.Module

class AddLessonFragment : Fragment() {

    private var moduleId: Int = -1

    private lateinit var db: AppDb
    private lateinit var adapter: AddLessonRvAdapter
    private lateinit var binding: FragmentAddLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            moduleId = it.getInt("moduleId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        val module = db.moduleDao().getModuleById(moduleId)
        binding = FragmentAddLessonBinding.inflate(inflater, container, false)

        binding.tvTop.text = module.name

        val list = ArrayList(db.lessonDao().getAllLessons().filter { it.moduleId == moduleId && it.courseId == module.courseId })
        adapter = AddLessonRvAdapter(list, object : AddLessonRvAdapter.AddLessonOnClickListener{
            override fun onBtnEditClick(lessonId: Int) {
                val bundle = Bundle()
                bundle.putInt("lessonId", lessonId)
                findNavController().navigate(R.id.editLessonFragment, bundle)
            }

            override fun onBtnDeleteClick(lesson: Lesson, position: Int) {
                val builder = AlertDialog.Builder(requireContext())

                builder.setMessage("Darsni o`chirishga rozimisiz?")
                builder.setNegativeButton("Yo`q") { dialog, _ -> dialog.dismiss() }
                builder.setPositiveButton("Ha") { dialog, _ ->
                    db.lessonDao().deleteLesson(lesson)
                    list.removeAt(position)

                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, list.size)

                    dialog.dismiss()
                }

                builder.show()
            }
        })

        binding.rv.adapter = adapter

        binding.svAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val about = binding.etAbout.text.toString()
            val position1 = binding.etPosition.text.toString()

            if (name.isNotEmpty() && about.isNotEmpty() && position1.isNotEmpty()) {
                if (db.lessonDao().getAllLessons().filter { it.name.equals(name, ignoreCase = true) && it.position == position1.toInt() }.count() == 0) {
                    val lesson = Lesson(name = name, about = about, position = position1.toInt(), image = module.image, moduleId = moduleId, courseId = module.courseId)
                    db.lessonDao().addLesson(lesson)
                    lesson.id = db.lessonDao().getIdByLesson(name, position1.toInt(), moduleId, module.courseId)

                    binding.etName.setText("")
                    binding.etAbout.setText("")
                    binding.etPosition.setText("")

                    list.add(lesson)
                    list.sortBy { it.position }
//                    adapter.notifyItemInserted(list.size)
                    adapter.notifyDataSetChanged()
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