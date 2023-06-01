package tw.edu.ncyu.drink.taskblitz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
class Tasks (
    @PrimaryKey(autoGenerate = true)
    var id : Int?=null,
    var title: String,
    var description: String,
    var category: String,
    var date: String,
    var time: String,
    var isFinished : Int = 0,
){
    override fun toString(): String {
        return "id: $id, Title: ${title.toString()}, description: $description, ${category.toString()}, ${date.toString()}, ${time.toString()}, $isFinished"
    }
}