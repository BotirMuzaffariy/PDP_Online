package uz.lycr.task2pdponline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.databinding.FragmentAboutLessonBinding
import uz.lycr.task2pdponline.db.AppDb

class AboutLessonFragment : Fragment() {

    private var param1: Int = -1

    private lateinit var db: AppDb
    private lateinit var binding: FragmentAboutLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("lessonId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        val lesson = db.lessonDao().getLessonById(param1)
        binding = FragmentAboutLessonBinding.inflate(inflater, container, false)

        binding.tvName.text = lesson.name
        binding.tvAbout.text = lesson.about
        binding.tvPosition.text = "${lesson.position}-dars"

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

}