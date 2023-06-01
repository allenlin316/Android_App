package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData

class TaskRepository(val dao: TaskDao) {

    fun insertTask(task: Tasks){
        dao.insertTask(task)
    }


    fun getAllTasks(): LiveData<List<Tasks>>{
        return dao.getAllTasks()
    }

    fun getTaskByDate(date: String): Tasks{
        return dao.getTaskByDate(date)
    }

    fun getFinishTasks(): LiveData<List<Tasks>>{
        return dao.getFinishTasks()
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