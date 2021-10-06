package uz.lycr.task2pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.databinding.ItemAllModulesRvBinding
import uz.lycr.task2pdponline.models.Module
import java.io.File

class AllModulesAdapter(
    var list: List<Module>,
    var listener: AllModulesOnClickListener,
    var courseName: String
) : RecyclerView.Adapter<AllModulesAdapter.AllModulesVh>() {

    inner class AllModulesVh(var itemBinding: ItemAllModulesRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(module: Module) {
            itemBinding.tvName.isSelected = true
            itemBinding.tvCourseName.isSelected = true

            itemBinding.tvName.text = module.name
            itemBinding.tvCourseName.text = courseName
            itemBinding.tvPosition.text = module.position.toString()
            if (module.image != "") itemBinding.ivImage.setImageURI(Uri.fromFile(File(module.image)))

            itemBinding.tvAgain.setOnClickListener { listener.onTvAgainClick(module.id) }
        }
    }

    interface AllModulesOnClickListener {
        fun onTvAgainClick(moduleId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllModulesVh {
        return AllModulesVh(
            ItemAllModulesRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllModulesVh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

}