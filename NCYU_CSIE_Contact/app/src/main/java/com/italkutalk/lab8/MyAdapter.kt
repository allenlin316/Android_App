package com.italkutalk.lab8

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val context: Context, private val data: ArrayList<Contact>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("shared_preference", MODE_PRIVATE)
    //實作 RecyclerView.ViewHolder 來儲存 View
    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        //連結畫面中的元件
        val tv_name = v.findViewById<TextView>(R.id.tv_name)
        val tv_lab = v.findViewById<TextView>(R.id.tv_lab)
        val tv_phone = v.findViewById<TextView>(R.id.tv_phone)
        val tv_email = v.findViewById<TextView>(R.id.tv_email)
    }

    // Define listener member variable
    var onItemClick: ((Contact) -> Unit)? = null

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
        holder.tv_name.text = data[position].name
        holder.tv_lab.text = data[position].lab
        holder.tv_phone.text = data[position].phone
        holder.tv_email.text = data[position].email
        // set click listener on item view
        holder.itemView.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("id", data[position].id)
            editor.putString("name", data[position].name)
            editor.putString("lab", data[position].lab)
            editor.putString("email", data[position].email)
            editor.putString("phone", data[position].phone)
            editor.apply()
            editor.commit()
            onItemClick?.invoke(data[position]) // 回傳該 item 回 OnItemClick
        }
    }
}