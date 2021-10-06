package uz.lycr.task2pdponline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.lycr.task2pdponline.databinding.FragmentEditModuleBinding
import uz.lycr.task2pdponline.db.AppDb

class EditModuleFragment : Fragment() {

    private var moduleId: Int = -1

    private lateinit var db: AppDb
    private lateinit var binding: FragmentEditModuleBinding

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
        binding = FragmentEditModuleBinding.inflate(inflater, container, false)

        binding.tvTop.text = module.name
        binding.etName.setText(module.name)
        binding.etPosition.setText(module.position.toString())

        binding.svSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val position = binding.etPosition.text.toString()

            if (name.isNotEmpty() && position.isNotEmpty()) {
                if (db.moduleDao().getAllModules().filter { it.name.equals(name, ignoreCase = true) && it.position == position.toInt() }.count() == 0) {
                    module.name = name
                    module.position = position.toInt()
                    db.moduleDao().updateModule(module)
                    findNavController().popBackStack()
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