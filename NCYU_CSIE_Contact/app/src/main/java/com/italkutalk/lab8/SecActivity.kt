package com.italkutalk.lab8

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SecActivity : AppCompatActivity() {
    private lateinit var dbrw: SQLiteDatabase
    //private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)
        dbrw = DBHelper(this).writableDatabase
        //將變數與 XML 元件綁定
        val btn_add = findViewById<Button>(R.id.btn_confirm)
        val ed_name = findViewById<EditText>(R.id.ed_name)
        val ed_lab = findViewById<EditText>(R.id.ed_lab)
        val ed_phone = findViewById<EditText>(R.id.ed_phone)
        val ed_email = findViewById<EditText>(R.id.ed_email)
        //設定按鈕監聽器
        btn_add.setOnClickListener {
            //判斷是否輸入資料
            when {
                ed_name.length() < 1 -> showToast("請輸入姓名")
                ed_phone.length() < 1 -> showToast("請輸入電話")
                ed_lab.length() < 1 -> showToast("請輸入實驗室名稱")
                ed_email.length() < 1 -> showToast("請輸入 email")
                else -> {
                    val b = Bundle()
                    try {
                        dbrw.execSQL("INSERT INTO Professors(name, lab, phone, email) VALUES(?, ?, ?, ?)",
                            arrayOf(ed_name.text.toString(), ed_lab.text.toString(), ed_phone.text.toString(), ed_email.text.toString())
                        )
                        showToast("新增: ${ed_name.text}, 實驗室: ${ed_lab.text}, 電話: ${ed_phone.text}, email: ${ed_email.text}")
                    } catch (e: Exception){
                        showToast("新增失敗: $e")
                    }
                    try{
                        val c = dbrw.rawQuery("SELECT teacher_id FROM Professors WHERE name='${ed_name.text}'", null)
                        c.moveToFirst()
                        val id = c.getInt(0)
                        b.putInt("id", id)
                        b.putString("name", ed_name.text.toString())
                        b.putString("phone", ed_phone.text.toString())
                        b.putString("lab", ed_lab.text.toString())
                        b.putString("email", ed_email.text.toString())
                    } catch (e: Exception){
                        showToast("回傳新增資料失敗: $e")
                    }
                    //使用 setResult()回傳聯絡人資料
                    setResult(Activity.RESULT_OK, Intent().putExtras(b))
                    finish()
                }
            }
        }
    }


    override fun onDestroy() {
        dbrw.close()//關閉資料庫

        super.onDestroy()
    }

    //建立 showToast 方法顯示 Toast 訊息
    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}