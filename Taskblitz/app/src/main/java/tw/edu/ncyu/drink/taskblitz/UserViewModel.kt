package tw.edu.ncyu.drink.taskblitz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class UserViewModel(application: Application): AndroidViewModel(application) {
    val repository: UserRepository
    init {
        val dao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
    }
    fun addUsers(user: Users){
        repository.insertUser(user)
    }

    fun isTaken(account: String): Boolean{
       return repository.isTaken(account)
    }

    fun isValidUser(account: String, password: String): Boolean{
        return repository.isValidUser(account, password)
    }

    fun getUserByAccount(account: String): Users {
        return repository.getUserByAccount(account)
    }

    fun getAllUsers(): LiveData<List<Users>> = repository.getAllUsers()
}