package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Tasks)

    @Query("Select * from Tasks")
    fun getAllTasks(): LiveData<List<Tasks>>

    @Query("SELECT * FROM Tasks WHERE date=:date AND isFinished=1")
    fun getFinishedTasksByDate(date: String): LiveData<List<Tasks>>

    @Query("SELECT * FROM Tasks WHERE isFinished=1")
    fun getFinishedTasks(): LiveData<List<Tasks>>

    @Query("Update Tasks Set isFinished = 1 where id=:uid")
    fun finishTask(uid: Int)

    @Query("UPDATE Tasks SET isFinished = 0 WHERE id=:id")
    fun undoTask(id: Int)
    @Update
    fun updateTask(task: Tasks)

    @Query("Delete from Tasks where id=:uid")
    fun deleteTask(uid:Int)
}