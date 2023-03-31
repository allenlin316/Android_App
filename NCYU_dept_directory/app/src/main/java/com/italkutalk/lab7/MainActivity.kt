package com.italkutalk.lab7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將變數與 XML 元件綁定
        val listView = findViewById<ListView>(R.id.listView)
        //val count = ArrayList<String>() //儲存購買數量資訊
        val item = ArrayList<Item>() //儲存系所資訊
        val icon_array =
            resources.obtainTypedArray(R.array.image_list) //從 R 類別讀取圖檔
        val phone_number_array =
            resources.obtainTypedArray(R.array.phone_list) // 從 R 類別讀取系所電話號碼
        val dept_name_array =
            resources.obtainTypedArray(R.array.dept_name_list) // 從 R 類別讀取系所名稱
        val campus_array =
            resources.obtainTypedArray(R.array.campus_list) // 從 R 類別讀取所在校區
        for(i in 0 until icon_array.length()) {
            val photo = icon_array.getResourceId(i,0) //系所圖片 Id
            val name = dept_name_array.getString(i).toString() //系所名稱
            val phone_number = phone_number_array.getString(i).toString() // 系所電話
            val campus = campus_array.getString(i).toString() // 所在校區
            item.add(Item(photo, name, phone_number, campus)) //新增系所資訊
        }
        icon_array.recycle() //釋放圖檔資源
        phone_number_array.recycle() //釋放圖檔資源
        dept_name_array.recycle() //釋放圖檔資源
        campus_array.recycle() //釋放圖檔資源
        listView.adapter = MyAdapter(this, item,
            R.layout.adapter_horizontal)
    }
}

//設計新的類別定義水果的資料結構
data class Item(
    val photo: Int, //圖片
    val name: String, //系所名稱
    val phone_number: String, //系所電話號碼
    val campus: String // 校區名稱
)