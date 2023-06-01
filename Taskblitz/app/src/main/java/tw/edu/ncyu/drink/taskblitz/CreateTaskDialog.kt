package tw.edu.ncyu.drink.taskblitz

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.w3c.dom.Text
import androidx.fragment.app.viewModels

class CreateTaskDialog: DialogFragment()  {

   // private val alertDialog: AlertDialog(requireContext())
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString("title") ?: "新增任務"
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .build()
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(title)

        // Inflate your custom layout
        val inflater = LayoutInflater.from(context)
        val customView = inflater.inflate(R.layout.dialog_create_task, null)
        builder.setView(customView)

        // Get references to the buttons in your custom layout
        val btn_save = customView.findViewById<Button>(R.id.btn_save)
        val btn_cancel = customView.findViewById<Button>(R.id.btn_cancel)
        val ed_name = customView.findViewById<EditText>(R.id.ed_name)
        val ed_category = customView.findViewById<TextView>(R.id.ed_category)
        val ed_description = customView.findViewById<EditText>(R.id.ed_description)
        val tv_date = customView.findViewById<TextView>(R.id.tv_date)
        val tv_time = customView.findViewById<TextView>(R.id.tv_time)
        // used in when update would auto fill in previous value
        val pre_name = arguments?.getString("name")?:"任務名稱"
        val pre_description = arguments?.getString("description")?:"寫點備註吧..."
        val pre_category = arguments?.getString("category") ?: "類別"
        val pre_date = arguments?.getString("date") ?: "日期>"
        val pre_time = arguments?.getString("time") ?: "時間>"
        val task_id = arguments?.getInt("id")

        if (title == "編輯任務"){
            ed_name.setText(pre_name)
            ed_category.setText(pre_category)
            tv_date.setText(pre_date)
            tv_time.setText(pre_time)
            ed_description.setText(pre_description)
        }

        tv_date.setOnClickListener{
            // Show the date picker
            val dialogBuilder = AlertDialog.Builder(requireContext())
            val datePicker = DatePicker(context)
            dialogBuilder.setView(datePicker)
                .setPositiveButton("OK") { dialog, which ->
                    val selectedYear = datePicker.year
                    val selectedMonth = datePicker.month + 1
                    val selectedDayOfMonth = datePicker.dayOfMonth

                    // Handle the selected date
                    tv_date.text = "${selectedYear.toString()}/${selectedMonth.toString()}/${selectedDayOfMonth.toString()}"

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }

            val dialog = dialogBuilder.create()
            dialog.show()
        }

        tv_time.setOnClickListener{
            val dialogBuilder = AlertDialog.Builder(requireContext())
            val timePicker = TimePicker(context)
            dialogBuilder.setView(timePicker)
                .setPositiveButton("OK"){ dialog, which ->
                    val selectedHour = timePicker.hour
                    val selectedMin = timePicker.minute

                    tv_time.text = "${selectedHour.toString()}:${selectedMin.toString()}"

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"){dialog, which ->
                    dialog.dismiss()
                }
            val dialog = dialogBuilder.create()
            dialog.show()
        }

        // Set click listeners for the buttons
        btn_save.setOnClickListener {
            // Handle OK button click
            val task = Tasks( null, ed_name.text.toString(),ed_description.text.toString(), ed_category.text.toString(), tv_date.text.toString(), tv_time.text.toString(), 0)
            if (title == "編輯任務"){
                Log.d("updated", task.toString())
                if (task != null) {
                    task.id = task_id
                    viewModel.updateTask(task)
                }
            } else{
                if (task != null) {
                    viewModel.insertTask(task)
                }
            }
            dismiss()
        }

        btn_cancel.setOnClickListener {
            // Handle Cancel button click
            dismiss()
        }

        return builder.create()
    }
}
