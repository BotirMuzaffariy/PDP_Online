package uz.lycr.task2pdponline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.adapters.AddLessonRvAdapter
import uz.lycr.task2pdponline.adapters.AllLessonsRvAdapter
import uz.lycr.task2pdponline.databinding.FragmentAllLessonsBinding
import uz.lycr.task2pdponline.db.AppDb

class AllLessonsFragment : Fragment() {

    private var moduleId: Int = -1

    private lateinit var db: AppDb
    private lateinit var binding: FragmentAllLessonsBinding

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
        binding = FragmentAllLessonsBinding.inflate(inflater, container, false)

        binding.tvTop.text = module.name

        binding.rv.adapter = AllLessonsRvAdapter(db.lessonDao().getAllLessons()
            .filter { it.moduleId == moduleId && it.courseId == module.courseId },
            object : AllLessonsRvAdapter.AllLessonsOnClickListener {
                override fun onItemClick(lessonId: Int) {
                    val bundle = Bundle()
                    bundle.putInt("lessonId", lessonId)
                    findNavController().navigate(R.id.aboutLessonFragment, bundle)
                }
            })

        return binding.root
    }

}