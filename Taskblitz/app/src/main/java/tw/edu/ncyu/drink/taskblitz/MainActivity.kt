package tw.edu.ncyu.drink.taskblitz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import tw.edu.ncyu.drink.taskblitz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // private variable to inflate the layout for the activity
    private lateinit var binding: ActivityMainBinding

    // variable to access the ViewModel class
    val viewModel : TaskViewModel  by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPage.adapter = ViewPagerAdapter(supportFragmentManager)

        // set onClickListener for the floating action button
        binding.btnAdd.setOnClickListener{
            // open add task dialog
            val  createTaskDialog = CreateTaskDialog()
            val arguments = Bundle().apply {
                putString("title", "新增任務")
            }
            createTaskDialog.arguments = arguments
            createTaskDialog.show(supportFragmentManager, "CreateTask")
        }

        // track the user swipes between fragments using the ViewPager, the BottomAppBar will be updated based on the selected page.
        binding.viewPage.addOnPageChangeListener(onPageChangeListener)

        binding.bottomNavigationMenu.setOnItemSelectedListener { menuItem->
            val adapter = ViewPagerAdapter(supportFragmentManager)
            when (menuItem.itemId) {
                 R.id.item_home -> {
                     binding.viewPage.currentItem =0
                    true
                }
                R.id.item_finished_task-> {
                    binding.viewPage.currentItem = 1
                    true
                }
                R.id.item_inbox-> {
                    binding.viewPage.currentItem = 2
                    true
                }
                else -> false
            }
        }


    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // Not needed for this implementation
        }
        override fun onPageSelected(position: Int) {
            // Update the BottomAppBar based on the selected page
            binding.bottomNavigationMenu.menu.getItem(position).isChecked = true
        }
        override fun onPageScrollStateChanged(state: Int) {
            // Not needed for this implementation
        }
    }

    private fun showToast(text: String) =
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    //回傳對應位置的 Fragment，決定頁面的呈現順序
    override fun getItem(position: Int) = when(position) {
        0 -> HomeFragment() //第一頁要呈現的 Fragment
        1 -> FinishedTaskFragment()
        else -> InboxFragment() //第三頁要呈現的 Fragment
    }
    //回傳 Fragment 頁數
    override fun getCount() = 3
}