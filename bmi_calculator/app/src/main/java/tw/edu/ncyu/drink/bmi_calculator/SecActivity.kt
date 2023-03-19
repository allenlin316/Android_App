package tw.edu.ncyu.drink.bmi_calculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        val btn_confirm = findViewById<Button>(R.id.btn_confirm)
        val ed_name = findViewById<EditText>(R.id.ed_name)
        val ed_height = findViewById<EditText>(R.id.ed_height)
        val ed_weight = findViewById<EditText>(R.id.ed_weight)

        btn_confirm.setOnClickListener {
            if (ed_name.length() < 1 || ed_height.length() < 1 || ed_weight.length() < 1)
                Toast.makeText(this, "all inputs are required", Toast.LENGTH_SHORT).show()
            else if (ed_height.text.toString().toFloat() > 100)
                Toast.makeText(this, "height should be in meters unit", Toast.LENGTH_SHORT).show()
            else{
                val height_Float = ed_height.text.toString().toFloat()
                val weight_Float = ed_weight.text.toString().toFloat()
                val bmi_result = weight_Float / (height_Float*height_Float)
                val b = Bundle()
                // get EditText content and add into Bundle
                b.putString("username", ed_name.text.toString())
                b.putInt("result", bmi_result.toInt())
                b.putString("user_bmi_status", getbmiWeightCategories(bmi_result))
                // using Activity.RESULT_OK to mark the status and record Intent
                setResult(Activity.RESULT_OK, Intent().putExtras(b))
                finish()
            }
        }
    }
    // get user's weight categories
    fun getbmiWeightCategories(bmi_result: Float) : String {
        if (bmi_result < 18.5)
            return "Underweight"
        else if(bmi_result >= 18.5 && bmi_result <= 24.9)
            return "Healthy Weight"
        else if(bmi_result >= 25 && bmi_result <= 29.9)
            return "Overweight"
        else if(bmi_result >= 30 && bmi_result <= 34.9)
            return "Obese"
        else if(bmi_result >=35 && bmi_result <= 39.9)
            return "Severely Obese"
        else
            return "Morbidly Obese"
    }
}