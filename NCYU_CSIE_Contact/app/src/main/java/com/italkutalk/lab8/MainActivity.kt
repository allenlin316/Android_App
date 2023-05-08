package com.italkutalk.lab8

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {
    private lateinit var dbrw: SQLiteDatabase
    private lateinit var adapter: MyAdapter
    private val contacts = ArrayList<Contact>()
    //接收回傳資料
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras?.let {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                // 讀取 bundle 的資料
                val id = it.getInt("id")?: return@let
                val name = it.getString("name") ?: return@let
                val lab = it.getString("lab") ?: return@let
                val phone = it.getString("phone") ?: return@let
                val email = it.getString("email") ?: return@let
                //新增聯絡人資料
                contacts.add(Contact(id, name, lab, phone, email))
                //更新列表
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 用來顯示上一次按的紀錄
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
        // 取得資料庫實體
        dbrw = DBHelper(this).writableDatabase
        //將變數與 XML 元件綁定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btn_add = findViewById<Button>(R.id.btn_add)
        //創建 LinearLayoutManager 物件，設定垂直排列
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        //創建 MyAdapter 並連結 recyclerView
        adapter = MyAdapter(this, contacts)
        recyclerView.adapter = adapter
        // 由 Adapter 按下 item 後的實作
        adapter.onItemClick = {contact ->
            Log.d("contact", contact.id.toString()) // debug 查看哪一個 item 被按
            // 將更新與刪除按鈕啟用
            findViewById<Button>(R.id.btn_update).isEnabled = true
            findViewById<Button>(R.id.btn_delete).isEnabled = true
            // 顯示點擊的 item 於頁面最上方 (shared preference)
            printSharedPreference()
            // 刪除某一個 item (教授)
            findViewById<Button>(R.id.btn_delete).setOnClickListener {
                // disable update and delete button
                findViewById<Button>(R.id.btn_update).isEnabled = false
                findViewById<Button>(R.id.btn_delete).isEnabled = false
                // create an alert builder
                AlertDialog.Builder(this)
                    .setTitle("是否確定要刪除?")
                // set the custom layout
                    .setCancelable(false)
                    .setMessage("刪除 ${contact.name} 教授")
                    .setPositiveButton("確認") { dialog, which ->
                        dbrw.execSQL("DELETE FROM Professors WHERE teacher_id='${contact.id.toString()}'")
                        findViewById<TextView>(R.id.tv_last_record).text = "刪除一項通訊錄" // sharedPreference 更新顯示
                        printDB() // 渲染
                    }
                    .setNegativeButton("取消") { dialog, which->
                        showToast("不刪除")
                    }.show()
            }

            // 編輯某一個 item (教授)
            findViewById<Button>(R.id.btn_update).setOnClickListener {
                // disable update and delete button
                findViewById<Button>(R.id.btn_update).isEnabled = false
                findViewById<Button>(R.id.btn_delete).isEnabled = false
                // create an alert builder
                val builder = AlertDialog.Builder(this)
                builder.setTitle("更新教授資料")
                // set the custom layout
                val customLayout: View = layoutInflater.inflate(R.layout.activity_sec, null)
                builder.setView(customLayout)
                // create and show the alert dialog
                val dialog = builder.create()
                customLayout.findViewById<Button>(R.id.btn_confirm).text = "更新"
                dialog.show()
                // fill in the known value
                val ed_name = customLayout.findViewById<EditText>(R.id.ed_name)
                val ed_lab = customLayout.findViewById<EditText>(R.id.ed_lab)
                val ed_email = customLayout.findViewById<EditText>(R.id.ed_email)
                val ed_phone = customLayout.findViewById<EditText>(R.id.ed_phone)
                ed_name.setText(contact.name)
                ed_lab.setText(contact.lab)
                ed_email.setText(contact.email)
                ed_phone.setText(contact.phone)
                // update Contact
                customLayout.findViewById<Button>(R.id.btn_confirm).setOnClickListener{
                    try{
                        dbrw.execSQL("UPDATE Professors SET name='${ed_name.text}', lab='${ed_lab.text}', email='${ed_email.text}', phone='${ed_phone.text}' WHERE teacher_id='${contact.id.toString()}'")
                        showToast("更新成功")
                        dialog.dismiss();
                        findViewById<TextView>(R.id.tv_last_record).text = "紀錄已更新" // sharedPreference 的更新
                        printDB()
                    } catch (e: Exception){
                        showToast("更新失敗: $e")
                    }
                }
            }
        }
        // 顯示點擊的 item 於頁面最上方
        printSharedPreference()
        // 渲染資料庫存在的資料
        printDB()
        //設定按鈕監聽器，使用 startActivityForResult()啟動 SecActivity
        btn_add.setOnClickListener {
            startActivityForResult(Intent(this, SecActivity::class.java), 1)
        }
    }

    private fun printSharedPreference(){
        // 顯示點擊的 item 於頁面最上方 (shared preference)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
        val sharedId = sharedPreferences.getInt("id", 0)
        val sharedName = sharedPreferences.getString("name", "None")
        val sharedLab = sharedPreferences.getString("lab", "None")
        val sharedEmail = sharedPreferences.getString("email", "None")
        val sharedPhone = sharedPreferences.getString("phone", "None")
        Log.d("shared_preference", "Id: ${sharedId.toString()}, Name: $sharedName, Lab: $sharedLab, Email: $sharedEmail, Phone: $sharedPhone")
        findViewById<TextView>(R.id.tv_last_record).setText("Name: $sharedName  Lab: $sharedLab\n  Email: $sharedEmail  Phone: $sharedPhone")
    }

    private fun printDB(){
        try{
            val c = dbrw.rawQuery("SELECT * FROM Professors", null)
            c.moveToFirst() // 從第一筆開始輸出
            showToast("共有 ${c.count} 筆資料")
            contacts.clear() // 刪除舊的資料
            for(i in 0 until c.count){
                // 加入新資料
                contacts.add(Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)))
                c.moveToNext() // 移動到下一筆
            }
            adapter.notifyDataSetChanged() // 更新列表資訊
            c.close() // 關閉 cursor
        } catch (e: Exception){
            showToast("顯示資料錯誤: $e")
        }
    }

    override fun onDestroy() {
        dbrw.close()//關閉資料庫
        super.onDestroy()
    }

    private fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

//設計新的類別定義聯絡人的資料結構
data class Contact (
    val id: Int,      // id 為 unique
    val name: String, //姓名
    val lab: String, // 實驗室名稱
    val phone: String, //電話
    val email: String // 實驗室 email
)