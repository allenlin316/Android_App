package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    //for single user insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: Users): Long

    // getting user data details
    @Query("SELECT * FROM Users WHERE account=:account")
    fun getUserByAccount(account: String):Users

    // validate user account if already exists
    @Query("SELECT EXISTS (SELECT * FROM Users WHERE account=:account)")
    fun isTaken(account: String): Boolean

    // validate user login
    @Query("SELECT EXISTS (SELECT * FROM Users WHERE account=:account AND password=:password)")
    fun isValidUser(account: String, password: String): Boolean

    // get all user data
    @Query("SELECT * FROM Users ORDER BY id DESC")
    fun getAllUsers(): LiveData<List<Users>>


}