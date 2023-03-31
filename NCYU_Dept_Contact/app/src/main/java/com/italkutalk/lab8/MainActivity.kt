package com.italkutalk.lab8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MyAdapter
    private val contacts = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將變數與 XML 元件綁定
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        val btn_add = findViewById<Button>(R.id.btn_add)
        //創建 LinearLayoutManager 物件，設定垂直排列
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        //創建 MyAdapter 並連結 recyclerView
        adapter = MyAdapter(contacts)
        recyclerView.adapter = adapter
        // 新增系所資料於 contact
        val icon_array =
            resources.obtainTypedArray(R.array.image_list) //從 R 類別讀取圖檔
        val phone_number_array =
            resources.obtainTypedArray(R.array.phone_list) // 從 R 類別讀取系所電話號碼
        val dept_name_array =
            resources.obtainTypedArray(R.array.dept_name_list) // 從 R 類別讀取系所名稱
        val campus_array =
            resources.obtainTypedArray(R.array.campus_list) // 從 R 類別讀取所在校區
        val dept_eng_array =
            resources.obtainTypedArray(R.array.dept_eng_name_list) // 從 R 類別讀取系所英文縮寫名稱
        for(i in 0 until icon_array.length()) {
            val photo = icon_array.getResourceId(i, 0) //系所圖片 Id
            val name = dept_name_array.getString(i).toString() //系所名稱
            val phone_number = phone_number_array.getString(i).toString() // 系所電話
            val campus = campus_array.getString(i).toString() // 所在校區
            val dept_eng = dept_eng_array.getString(i).toString() // 系所英文縮寫
            contacts.add(Contact(photo, name, phone_number, campus, dept_eng)) //新增系所資訊
        }
        icon_array.recycle() //釋放圖檔資源
        phone_number_array.recycle() //釋放圖檔資源
        dept_name_array.recycle() //釋放圖檔資源
        campus_array.recycle() //釋放圖檔資源
    }
}

//設計新的類別定義聯絡人的資料結構
data class Contact (
    val photo: Int, //圖片
    val name: String, //系所名稱
    val phone: String, //系所電話號碼
    val campus: String, // 校區名稱
    val dept_eng: String // 系所英文的縮寫
)