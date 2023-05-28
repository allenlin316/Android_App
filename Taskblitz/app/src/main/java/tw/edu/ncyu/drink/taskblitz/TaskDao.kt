package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
//    @Insert()
//    suspend fun insertTask(tasks: Tasks):Long

    @Query("Select * from Tasks where isFinished=0")
    fun getTask(): LiveData<List<Tasks>>

    @Query("Update Tasks Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from Tasks where id=:uid")
    fun deleteTask(uid:Long)
}