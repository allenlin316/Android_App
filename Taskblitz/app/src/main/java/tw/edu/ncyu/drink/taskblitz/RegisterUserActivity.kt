package tw.edu.ncyu.drink.taskblitz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import tw.edu.ncyu.drink.taskblitz.databinding.ActivityLoginPageBinding

class RegisterUserActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    public var isAllowed: Boolean = false

    val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener{ // open an alert dialogue to sign up
            signup(it)
        }
        loginUser()
    }

    private fun signup(it: View?) {
        // create an alert builder
        val builder = AlertDialog.Builder(this)
        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.dialog_register_page, null)
        builder.setView(customLayout)
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
        // check if user exists
        val ed_account = customLayout.findViewById<EditText>(R.id.ed_account)
        ed_account.addTextChangedListener(object: TextWatcher {
            // check account existence every time user input
            override fun afterTextChanged(s: Editable) {
                val account = s.toString()
                Log.d("afterTextChanged", "afterTextChanged: $account")
                val ed_password = customLayout.findViewById<EditText>(R.id.ed_password)
                ed_password.setOnFocusChangeListener { view, hasFocus ->
                    if(hasFocus){
                        Log.d("clicked edPassword", account)
                        val isAccountExists = viewModel.isTaken(account)
                        if(isAccountExists){
                            showToast("帳號被用過囉")
                            val tv_alert = customLayout.findViewById<TextView>(R.id.tv_alert_account)
                            tv_alert.text = "帳號被使用過囉"
                            tv_alert.setTextColor(Color.RED)
                            tv_alert.visibility = View.VISIBLE
                            isAllowed = false
                        }
                        else {
                            val tv_alert = customLayout.findViewById<TextView>(R.id.tv_alert_account)
                            tv_alert.text = "符合"
                            tv_alert.setTextColor(Color.GREEN)
                            tv_alert.visibility = View.VISIBLE
                            isAllowed = true
                        }
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        // check if register successfully
        customLayout.findViewById<Button>(R.id.btn_register).setOnClickListener{
            val account = customLayout.findViewById<EditText>(R.id.ed_account).text.toString()
            val password = customLayout.findViewById<EditText>(R.id.ed_password).text.toString()
            val check_password = customLayout.findViewById<EditText>(R.id.ed_check_password).text.toString()
            if(password == check_password){
                createUser(account, password)
                dialog.dismiss()
            } else{
                showToast("確認密碼輸入錯誤")
                val tv_alert = customLayout.findViewById<TextView>(R.id.tv_alert_pwd)
                tv_alert.text = "確認密碼錯誤"
                tv_alert.setTextColor(Color.RED)
                tv_alert.visibility = View.VISIBLE
            }
        }
    }

    private fun loginUser() {
        binding.edAccount.addTextChangedListener(object: TextWatcher {
            // check account existence every time user input
            override fun afterTextChanged(s: Editable) {
                val account = s.toString()
                //Log.d("afterTextChanged", "afterTextChanged: $account")
                binding.edPassword.setOnFocusChangeListener { view, hasFocus ->
                    if(hasFocus){
                        Log.d("clicked edPassword", account)
                        val isAccountExists = viewModel.isTaken(account)
                        if(isAccountExists){
                            showToast("帳號存在")
                            val tv_alert = binding.tvAlertAccount
                            tv_alert.text = "符合"
                            tv_alert.setTextColor(Color.GREEN)
                            tv_alert.visibility = View.VISIBLE
                            isAllowed = true
                        }
                        else {
                            val tv_alert = binding.tvAlertAccount
                            tv_alert.text = "帳號不存在"
                            tv_alert.setTextColor(Color.RED)
                            tv_alert.visibility = View.VISIBLE
                            isAllowed = false
                        }
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        binding.btnLogin.setOnClickListener{
            val account = binding.edAccount.text.toString()
            val password = binding.edPassword.text.toString()
            val isValidUser = viewModel.isValidUser(account, password)
            if(isValidUser){
                showToast("Welcome $account")
            } else{
                showToast("帳號或密碼錯誤")
            }
        }
    }

    // function to create new User
    // the addUsers function from the ViewModel class
    private fun createUser(account: String, password: String) {
        if(isAllowed) {
            // create new User object
            val user = Users(null, account, password)
            // call addUsers function from the ViewModel class
            viewModel.addUsers(user)
            // display a Toast message to confirm the register
            showToast("成功儲存 $account, $password")
            // start MainActivity
            //startActivity(Intent(this@RegisterUserActivity,MainActivity::class.java))
        } else{
            showToast("帳號被用過囉")
        }
    }

    private fun showToast(text: String) =
        Toast.makeText(this@RegisterUserActivity, text, Toast.LENGTH_LONG).show()
}