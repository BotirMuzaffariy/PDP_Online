package uz.lycr.task2pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.databinding.ItemAddModuleRvBinding
import uz.lycr.task2pdponline.models.Module
import java.io.File

class AddModuleRvAdapter(var list: List<Module>, var listener: AddModuleOnClickListener) :
    RecyclerView.Adapter<AddModuleRvAdapter.AddModuleVh>() {

    inner class AddModuleVh(var itemBinding: ItemAddModuleRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(module: Module, position: Int) {
            itemBinding.tvName.isSelected = true
            itemBinding.tvName.text = module.name
            itemBinding.tvPosition.text = module.position.toString()
            if (module.image != "") itemBinding.ivImage.setImageURI(Uri.fromFile(File(module.image)))

            itemBinding.cv.setOnClickListener { listener.onItemClick(module.id) }
            itemBinding.cvEdit.setOnClickListener { listener.onBtnEditClick(module.id) }
            itemBinding.cvDelete.setOnClickListener { listener.onBtnDeleteClick(module, position) }
        }
    }

    interface AddModuleOnClickListener {
        fun onItemClick(moduleId: Int)
        fun onBtnEditClick(moduleId: Int)
        fun onBtnDeleteClick(module: Module, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddModuleVh {
        return AddModuleVh(
            ItemAddModuleRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddModuleVh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

}