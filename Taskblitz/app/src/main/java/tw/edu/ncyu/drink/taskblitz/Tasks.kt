package tw.edu.ncyu.drink.taskblitz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
class Tasks (
    @PrimaryKey(autoGenerate = true)
    var id : Int?=null,
    var user_id: Int,
    var title: String,
    var description: String,
    var category: String,
    var date: Long,
    var time: Long,
    var isFinished : Int = 0,
)