package tw.edu.ncyu.drink.taskblitz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val repository: TaskRepository
    init {
        val dao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
    }

    fun insertTask(task: Tasks){
        repository.insertTask(task)
    }

    fun getAllTasks(): LiveData<List<Tasks>>{
        return repository.getAllTasks()
    }

    fun getFinishedTasks(): LiveData<List<Tasks>>{
        return repository.getFinishedTasks()
    }

    fun getFinishedTasksByDate(date: String): LiveData<List<Tasks>>{
        return repository.getFinishedTasksByDate(date)
    }

    fun finishTask(uid: Int){
        repository.finishTask(uid)
    }

    fun undoTask(id: Int){
        repository.undoTask(id)
    }

    fun updateTask(task: Tasks){
        repository.updateTask(task)
    }

    fun deleteTask(uid: Int){
        repository.deleteTask(uid)
    }
}