package com.italkutalk.lab8

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val data: ArrayList<Contact>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    //實作 RecyclerView.ViewHolder 來儲存 View
    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        //連結畫面中的元件
        val img_photo = v.findViewById<ImageView>(R.id.img_photo)
        val tv_dept = v.findViewById<TextView>(R.id.tv_dept)
        val tv_campus = v.findViewById<TextView>(R.id.tv_campus)
        val tv_phone = v.findViewById<TextView>(R.id.tv_phone)
    }
    //回傳資料數量
    override fun getItemCount() = data.size
    //建立 ViewHolder 與 Layout 並連結彼此
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int):
            ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false)
        return ViewHolder(v)
    }
    //將資料指派給元件呈現
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img_photo.setImageResource(data[position].photo)
        holder.tv_dept.text = data[position].name
        holder.tv_phone.text = data[position].phone
        holder.tv_campus.text = data[position].campus

        holder.itemView.setOnClickListener {view ->
            //val intent = Intent()
            //intent.action = Intent.ACTION_VIEW
            //intent.data = Uri.parse("https://google.com")
            val ncyu_url = "https://www.ncyu.edu.tw/"
            val queryUrl: Uri = Uri.parse("${ncyu_url}/${data[position].dept_eng}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            view.context.startActivity(intent)
        }
    }
}