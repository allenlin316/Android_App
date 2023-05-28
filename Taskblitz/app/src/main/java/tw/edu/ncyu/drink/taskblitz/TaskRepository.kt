package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData

class TaskRepository(val dao: TaskDao) {

    fun getUserAllTasks(user_id: Int): LiveData<List<Tasks>>{
        return dao.getUserAllTasks(user_id)
    }

    fun getAllTasks(): LiveData<List<Tasks>>{
        return dao.getAllTasks()
    }

    fun finishTask(uid:Long){
        dao.finishTask(uid)
    }

    fun deleteTask(uid:Long){
        dao.deleteTask(uid)
    }

}