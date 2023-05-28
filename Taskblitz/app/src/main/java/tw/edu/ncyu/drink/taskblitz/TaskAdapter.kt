package tw.edu.ncyu.drink.taskblitz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.edu.ncyu.drink.taskblitz.databinding.TasksLayoutBinding

class TaskAdapter(val context: Context, val list: List<Tasks>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    // Inner ViewHolder class
    class ViewHolder(val binding: TasksLayoutBinding): RecyclerView.ViewHolder(binding.root){}
    // DAO instance to interact with the database
    private val dao = AppDatabase.getDatabase(context).taskDao()

    // function to inflate the layout for each contact and create a new Viewholder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TasksLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = list[position].title
        holder.binding.tvDate.text = list[position].date.toString()

//        holder.binding.deleteButton.setOnClickListener{
//            dao.delete(list[position])
//            notifyItemRemoved(position)
//        }
//        holder.itemView.setOnClickListener{
//            val intent = Intent(Intent.ACTION_CALL, Uri.parse("" + list[position].number))
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}