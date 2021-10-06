package uz.lycr.task2pdponline

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import uz.lycr.task2pdponline.adapters.SettingsRvAdapter
import uz.lycr.task2pdponline.databinding.FragmentSettingsBinding
import uz.lycr.task2pdponline.databinding.ItemDialogAddImgBinding
import uz.lycr.task2pdponline.db.AppDb
import uz.lycr.task2pdponline.models.Course
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SettingsFragment : Fragment() {

    private var imgPath = ""

    private lateinit var db: AppDb
    private lateinit var adapter: SettingsRvAdapter
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDb.getInstance(requireContext())
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val list = ArrayList(db.courseDao().getAllCourses())

        binding.svAdd.setOnClickListener {
            val name = binding.etName.text.toString()

            if (name.isNotEmpty()) {
                if (db.courseDao().getAllCourses().filter { it.name.equals(name, ignoreCase = true) }.count() == 0) {
                    val course = Course(name = name, image = imgPath)
                    db.courseDao().addCourse(course)
                    course.id = db.courseDao().getIdByCourse(name)

                    imgPath = ""
                    list.add(course)
                    binding.etName.setText("")
                    adapter.notifyItemInserted(list.size)
                    binding.ivImage.setImageResource(R.drawable.placeholder)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Bu kurs allaqachon qo`shilgan !!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Kurs nomini kiriting", Toast.LENGTH_SHORT).show()
            }
        }

        adapter = SettingsRvAdapter(list, object : SettingsRvAdapter.SettingsOnClickListener {
            override fun onItemClick(courseId: Int) {
                val bundle = Bundle()
                bundle.putInt("courseId", courseId)
                findNavController().navigate(R.id.addModuleFragment, bundle)
            }

            override fun onBtnEditClick(courseId: Int) {
                val bundle = Bundle()
                bundle.putInt("courseId", courseId)
                findNavController().navigate(R.id.editCourseFragment, bundle)
            }

            override fun onBtnDeleteClick(course: Course, position: Int) {
                val builder = AlertDialog.Builder(requireContext())

                builder.setMessage("Bu kurs ichiga modullar kiritilgan bo`lishi mumkin. Modullar bilan birgalikda o`chib ketishiga rozimisiz?")
                builder.setNegativeButton("Yo`q") { dialog, _ -> dialog.dismiss() }
                builder.setPositiveButton("Ha") { dialog, _ ->
                    list.removeAt(position)
                    db.courseDao().deleteCourse(course)

                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, list.size)

                    dialog.dismiss()
                }

                builder.show()
            }
        })

        binding.rv.adapter = adapter

        binding.ivImage.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val dialogBinding = ItemDialogAddImgBinding.inflate(layoutInflater)

            builder.setView(dialogBinding.root)
            val dialog = builder.create()

            dialogBinding.tvCamera.setOnClickListener {
                getImgFromCamera()
                dialog.dismiss()
            }

            dialogBinding.tvGallery.setOnClickListener {
                getImgFromGallery()
                dialog.dismiss()
            }

            dialog.show()
        }

        return binding.root
    }

    private fun getImgFromCamera() {
        askPermission(Manifest.permission.CAMERA) {
            val imageFile = createImageFile()
            val uri: Uri =
                FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, imageFile)
            getCameraContent.launch(uri)
        }.onDeclined {
            if (it.hasDenied()) it.askAgain()
            if (it.hasForeverDenied()) it.goToSettings()
        }
    }

    private fun getImgFromGallery() {
        askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
            getGalleryContent.launch("image/*")
        }.onDeclined {
            if (it.hasDenied()) it.askAgain()
            if (it.hasForeverDenied()) it.goToSettings()
        }
    }

    private val getGalleryContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.ivImage.setImageURI(it)

                val openInputStream = (activity as MainActivity).contentResolver.openInputStream(it)
                val file =
                    File((activity as MainActivity).filesDir, "${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)

                openInputStream?.copyTo(outputStream)
                openInputStream?.close()

                imgPath = file.absolutePath
            }
        }

    private val getCameraContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) binding.ivImage.setImageURI(Uri.fromFile(File(imgPath))) else imgPath = ""
        }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val filesDir =
            (activity as MainActivity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile("${System.currentTimeMillis()}", ".jpg", filesDir)
        imgPath = tempFile.absolutePath
        return tempFile
    }
}