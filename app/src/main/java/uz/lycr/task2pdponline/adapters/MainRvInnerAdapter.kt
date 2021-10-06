package uz.lycr.task2pdponline.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.lycr.task2pdponline.databinding.ItemMainInnerRvBinding
import uz.lycr.task2pdponline.models.Module

class MainRvInnerAdapter(var list: List<Module>, var listener: MainInnerRvClickListener): RecyclerView.Adapter<MainRvInnerAdapter.MainInnerVh>() {

    inner class MainInnerVh(var itemBinding: ItemMainInnerRvBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(module: Module) {
            itemBinding.tvName.text = module.name
            itemBinding.sv.setOnClickListener { listener.onItemClick(module.id) }
        }
    }

    interface MainInnerRvClickListener{
        fun onItemClick(moduleId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainInnerVh {
        return MainInnerVh(ItemMainInnerRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainInnerVh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

}