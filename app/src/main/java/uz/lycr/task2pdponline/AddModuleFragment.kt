package uz.lycr.task2pdponline

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.adapters.AddModuleRvAdapter
import uz.lycr.task2pdponline.databinding.FragmentAddModuleBinding
import uz.lycr.task2pdponline.db.AppDb
import uz.lycr.task2pdponline.models.Course
import uz.lycr.task2pdponline.models.Module

class AddModuleFragment : Fragment() {

    private var courseId: Int = -1

    private lateinit var db: AppDb
    private lateinit var adapter: AddModuleRvAdapter
    private lateinit var binding:FragmentAddModuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseId = it.getInt("courseId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        val course = db.courseDao().getCourseById(courseId)
        binding = FragmentAddModuleBinding.inflate(inflater, container, false)

        binding.tvTop.text = course.name

        val list = ArrayList(db.moduleDao().getAllModules().filter { it.courseId == courseId })

        adapter = AddModuleRvAdapter(list, object: AddModuleRvAdapter.AddModuleOnClickListener{
            override fun onItemClick(moduleId: Int) {
                val bundle = Bundle()
                bundle.putInt("moduleId", moduleId)
                findNavController().navigate(R.id.addLessonFragment, bundle)
            }

            override fun onBtnEditClick(moduleId: Int) {
                val bundle = Bundle()
                bundle.putInt("moduleId", moduleId)
                findNavController().navigate(R.id.editModuleFragment, bundle)
            }

            override fun onBtnDeleteClick(module: Module, position: Int) {
                val builder = AlertDialog.Builder(requireContext())

                builder.setMessage("Bu modul ichiga darslar kiritilgan bo`lishi mumkin. Darslar bilan birgalikda o`chib ketishiga rozimisiz?")
                builder.setNegativeButton("Yo`q") { dialog, _ -> dialog.dismiss() }
                builder.setPositiveButton("Ha") { dialog, _ ->
                    db.moduleDao().deleteModule(module)
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
            val position1 = binding.etPosition.text.toString()

            if (name.isNotEmpty() && position1.isNotEmpty()) {
                if (db.moduleDao().getAllModules().filter { it.courseId == courseId && it.name.equals(name, ignoreCase = true) && it.position == position1.toInt() }.count() == 0) {
                    val module = Module(name = name, position = position1.toInt(), image = course.image, courseId = courseId)
                    db.moduleDao().addModule(module)
                    module.id = db.moduleDao().getIdByModule(name, position1.toInt(), module.courseId)

                    binding.etName.setText("")
                    binding.etPosition.setText("")

                    list.add(module)
                    list.sortBy { it.position }
//                    adapter.notifyItemInserted(list.size)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Bu modul allaqachon qo`shilgan !!!",
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