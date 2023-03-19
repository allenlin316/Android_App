package tw.edu.ncyu.drink.bmi_calculator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // associate variable with XML
        val btn_bmi = findViewById<Button>(R.id.btn_bmi)

        btn_bmi.setOnClickListener{
            // change to SecActivity and pass requestCode as identification
            val intent = Intent(this, SecActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val tv_result = findViewById<TextView>(R.id.tv_result)
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let{
            // validate request code and status of SecActivity
            if(requestCode == 1 && resultCode == Activity.RESULT_OK){
                // read Bundle data
                tv_result.text =
                            "${it.getString("username")} BMI is ${it.getInt("result")}\n\n" +
                            "your weight categories is ${it.getString("user_bmi_status")}"
            }
        }
    }


}