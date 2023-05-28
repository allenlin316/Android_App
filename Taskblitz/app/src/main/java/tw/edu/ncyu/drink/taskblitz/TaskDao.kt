package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
//    @Insert()
//    suspend fun insertTask(tasks: Tasks):Long

    // get certain user's all tasks
    @Query("SELECT * FROM Tasks WHERE user_id=:user_id")
    fun getUserAllTasks(user_id: Int): LiveData<List<Tasks>>

    @Query("Select * from Tasks where isFinished=0")
    fun getAllTasks(): LiveData<List<Tasks>>

    @Query("Update Tasks Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from Tasks where id=:uid")
    fun deleteTask(uid:Long)
}