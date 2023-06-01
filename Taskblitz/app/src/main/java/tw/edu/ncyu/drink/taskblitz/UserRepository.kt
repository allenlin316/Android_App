package tw.edu.ncyu.drink.taskblitz

import androidx.lifecycle.LiveData

class UserRepository(val dao: UserDao) {

    // function to get all users from the database
    fun getAllUsers(): LiveData<List<Users>> {
        return dao.getAllUsers()
    }

    fun insertUser(users: Users){
        dao.insertUser(users)
    }

    fun isTaken(account: String): Boolean{
        return dao.isTaken(account)
    }

    fun isValidUser(account: String, password: String): Boolean{
        return dao.isValidUser(account, password)
    }

    fun getUserByAccount(account: String):Users {
        return dao.getUserByAccount(account)
    }

}