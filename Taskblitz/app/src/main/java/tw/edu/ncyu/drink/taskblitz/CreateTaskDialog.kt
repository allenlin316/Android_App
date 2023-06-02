package tw.edu.ncyu.drink.taskblitz

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

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
        val checkbox_remind = customView.findViewById<CheckBox>(R.id.checkbox_remind)
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
                    val selectedMin = String.format("%02d", timePicker.minute)

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
            if (checkbox_remind.isChecked)
                scheduleNotification(ed_name.text.toString(), ed_description.text.toString(), tv_date.text.toString(), tv_time.text.toString())
            dismiss()
        }

        btn_cancel.setOnClickListener {
            // Handle Cancel button click
            dismiss()
        }

        return builder.create()
    }

    private fun scheduleNotification(title: String, description: String, date: String, time: String) {
        val intent = Intent(requireContext(), Notification::class.java)
        val title = title
        val message = description
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTimeInMillis(date, time)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        Toast.makeText(requireContext(), "已新增提醒", Toast.LENGTH_LONG).show()
    }

    private fun getTimeInMillis(date: String, time: String): Long {
        val date_group = date.split("/")
        val year = date_group[0].toInt()
        val month = date_group[1].toInt() - 1
        val day = date_group[2].toInt()
        val time_group = time.split(":")
        val hour = time_group[0].toInt()
        val min = time_group[1].toInt()

        Log.d("getTime", "$year, $month, $day, $hour, $min")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, min, 0)
        Log.d("calendar time", calendar.time.toString())

        return calendar.timeInMillis
    }
}
