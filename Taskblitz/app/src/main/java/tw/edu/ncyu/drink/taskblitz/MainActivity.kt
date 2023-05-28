package tw.edu.ncyu.drink.taskblitz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import tw.edu.ncyu.drink.taskblitz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // private variable to inflate the layout for the activity
    private lateinit var binding: ActivityMainBinding

    // variable to access the ViewModel class
    val viewModel : UserViewModel  by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set onClickListener for the floating action button
//        binding.btnAdd.setOnClickListener{
//            val intent = Intent(this , CreateContactActivity::class.java)
//            startActivity(intent)
//        }
//
//        // Observe the LiveData returned by the getAllContacts method
//        viewModel.getAllContacts().observe(this , Observer {  list->
//            // set the layout manager and the adapter for the recycler view
//            binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//            binding.recyclerView.adapter = ContactAdapter(this,list)
//        })
    }
}