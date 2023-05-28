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

    fun getAllTasks(): LiveData<List<Tasks>>{
        return repository.getAllTasks()
    }

    fun finishTask(uid: Long){
        repository.finishTask(uid)
    }

    fun deleteTask(uid: Long){
        repository.deleteTask(uid)
    }
}