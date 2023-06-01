package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData

class TaskRepository(val dao: TaskDao) {

    fun insertTask(task: Tasks){
        dao.insertTask(task)
    }


    fun getAllTasks(): LiveData<List<Tasks>>{
        return dao.getAllTasks()
    }

    fun getFinishedTasksByDate(date: String): LiveData<List<Tasks>>{
        return dao.getFinishedTasksByDate(date)
    }

    fun getFinishedTasks(): LiveData<List<Tasks>>{
        return dao.getFinishedTasks()
    }

    fun finishTask(uid:Int){
        dao.finishTask(uid)
    }

    fun undoTask(id: Int){
        dao.undoTask(id)
    }

    fun updateTask(task: Tasks){
        dao.updateTask(task)
    }

    fun deleteTask(uid:Int){
        dao.deleteTask(uid)
    }

}