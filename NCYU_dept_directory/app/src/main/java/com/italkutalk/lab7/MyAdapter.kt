package com.italkutalk.lab7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(context: Context, data: ArrayList<Item>, private val layout: Int) : ArrayAdapter<Item>(context, layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //依據傳入的 Layout 建立畫面
        val view = View.inflate(parent.context, layout, null)
        //依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        //將圖片指派給 ImageView 呈現
        val img_photo = view.findViewById<ImageView>(R.id.img_photo)
        img_photo.setImageResource(item.photo)

        val tv_dept = view.findViewById<TextView>(R.id.tv_dept)
        val tv_phone = view.findViewById<TextView>(R.id.tv_phone)
        val tv_campus = view.findViewById<TextView>(R.id.tv_campus)
        tv_dept.text = "${item.name}"
        tv_phone.text = "${item.phone_number}"
        tv_campus.text = "${item.campus}"
        //回傳此項目的畫面
        return view
    }
}