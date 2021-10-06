package uz.lycr.task2pdponline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.adapters.MainRvAdapter
import uz.lycr.task2pdponline.databinding.FragmentMainBinding
import uz.lycr.task2pdponline.db.AppDb
import uz.lycr.task2pdponline.models.Course

class MainFragment : Fragment() {

    private lateinit var db: AppDb
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.svSettings.setOnClickListener { findNavController().navigate(R.id.settingsFragment) }

        binding.rv.adapter = MainRvAdapter(
            db.courseDao().getAllCourses(),
            object : MainRvAdapter.MainRvOnClickListener {
                override fun onTvAllClick(courseId: Int) {
                    val bundle = Bundle()
                    bundle.putInt("course_id", courseId)
                    findNavController().navigate(R.id.allModulesFragment, bundle)
                }
            })

        return binding.root
    }

}