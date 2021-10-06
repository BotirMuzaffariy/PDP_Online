package uz.lycr.task2pdponline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.adapters.AllModulesAdapter
import uz.lycr.task2pdponline.databinding.FragmentAllModulesBinding
import uz.lycr.task2pdponline.db.AppDb

class AllModulesFragment : Fragment() {

    private var courseId: Int = -1

    private lateinit var db: AppDb
    private lateinit var binding: FragmentAllModulesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseId = it.getInt("course_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        val course = db.courseDao().getCourseById(courseId)
        binding = FragmentAllModulesBinding.inflate(inflater, container, false)

        binding.tvTop.text = course.name

        binding.rv.adapter = AllModulesAdapter(
            db.moduleDao().getAllModules().filter { it.courseId == courseId },
            object : AllModulesAdapter.AllModulesOnClickListener {
                override fun onTvAgainClick(moduleId: Int) {
                    val bundle = Bundle()
                    bundle.putInt("moduleId", moduleId)
                    findNavController().navigate(R.id.allLessonsFragment, bundle)
                }
            },
            course.name
        )

        return binding.root
    }

}