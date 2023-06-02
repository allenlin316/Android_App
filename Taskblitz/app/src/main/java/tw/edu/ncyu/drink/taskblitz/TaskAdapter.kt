package tw.edu.ncyu.drink.taskblitz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import tw.edu.ncyu.drink.taskblitz.databinding.HomeItemLayoutBinding

class TaskAdapter(private val viewModel: TaskViewModel, val list: List<Tasks>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    // Inner ViewHolder class
    class ViewHolder(val binding: HomeItemLayoutBinding): RecyclerView.ViewHolder(binding.root){}

    // Define listener member variable
    var onItemClick: ((Tasks) -> Unit)? = null
    // function to inflate the layout for each contact and create a new Viewholder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = list[position].title
        holder.binding.tvDate.text = list[position].date.toString()
        holder.binding.tvCategory.text = list[position].category
        holder.binding.tvTime.text = list[position].time.toString()
        holder.binding.checkBox.isChecked = (list[position].isFinished == 1)


        holder.binding.btnDelete.setOnClickListener{
            list[position].id?.let { it1 -> viewModel.deleteTask(it1) }
            notifyItemRemoved(position)
        }

        holder.binding.checkBox.setOnClickListener {
            //holder.binding.checkBox.setImageResource(android.R.drawable.checkbox_on_background)
            holder.binding.checkBox.isChecked = list[position].isFinished != 1
            Log.d("checked", holder.binding.checkBox.isChecked.toString())
            if (list[position].isFinished == 0){
                list[position].id?.let { it1 -> viewModel.finishTask(it1) }
            } else{
                list[position].id?.let {it1 -> viewModel.undoTask(it1)}
            }
        }

        holder.itemView.setOnClickListener{
                // Handle item click here
            onItemClick?.invoke(list[position]) // 回傳該 item 回 OnItemClick
        }
    }

    fun getItemAtPosition(position: Int): Tasks {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}