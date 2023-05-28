package tw.edu.ncyu.drink.taskblitz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
class Users(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var account: String,
    var password: String
)