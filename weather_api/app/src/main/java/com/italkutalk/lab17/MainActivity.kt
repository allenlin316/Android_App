package com.italkutalk.lab17

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {
    private lateinit var btn_query: Button
    // 定義資料結構存放 Server 回傳的資料
    // 定義的有: 抓到此資料時的時間、地點、日落日出時間(>sys>)、氣溫與體感溫度與濕度(>main>)、風向(>wind>deg)、天氣狀況與圖示(>weather>0>)

    class MyObject {
        var dt: Long = 0
        var timezone: Int = 0
        var name: String = ""
        lateinit var weather: Array<Weather>
        lateinit var wind: Wind
        lateinit var sys: Sys
        lateinit var main: Main
    }

        class Main{
            var temp: Float = 0f
            var feels_like: Float = 0f
            var humidity: Int = 0
        }

        class Sys{
            var sunrise: Long = 0
            var sunset: Long = 0
        }

        class Weather{
            var description: String = ""
            var icon: String = ""
        }

        class Wind{
            var deg: Float = 0f
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_query = findViewById(R.id.btn_query)
        btn_query.setOnClickListener{
            btn_query.isEnabled = false
            sendRequest()
        }
    }
    //發送請求
    private fun sendRequest() {
        //本書原內容採用環保署空氣品質指標 API，但近期對方修改資料的取得方式，故範例提供更穩定的資料來源
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=22.9908&lon=120.2133&appid={Ur API Key}"

        //建立 Request.Builder 物件，藉由 url()將網址傳入，再建立 Request 物件
        val req = Request.Builder()
            .url(url)
            .build()
        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response){
                val json = response.body?.string()
                val myObject = Gson().fromJson(json, MyObject::class.java)
                showDialog(myObject)
            }

            override fun onFailure(call: Call, e: IOException){
                runOnUiThread{
                    btn_query.isEnabled = true
                    Toast.makeText(this@MainActivity, "查詢失敗$e", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    //顯示結果
    private fun showDialog(myObject: MyObject) {

        //切換到主執行緒將畫面更新
        runOnUiThread{
            btn_query.isEnabled = true
            // create an alert builder
            val builder = AlertDialog.Builder(this)
            builder.setTitle("天氣")
            // set the custom layout
            val customLayout: View = layoutInflater.inflate(R.layout.dialog_layout, null)
            var tv_location = customLayout.findViewById<TextView>(R.id.tv_location)
            var tv_content = customLayout.findViewById<TextView>(R.id.tv_content)
            var weather_icon = customLayout.findViewById<ImageView>(R.id.weather_icon)
            val simpleDate1 = SimpleDateFormat("yyyy/M/dd hh:mm:ss")
            val timestamp_dt = Date((myObject.dt + myObject.timezone)*1000)
            val simpleDate2 = SimpleDateFormat("hh:mm:ss a")
            val timestamp_sunrise = Date((myObject.sys.sunrise + myObject.timezone)*1000)
            val timestamp_sunset = Date((myObject.sys.sunset + myObject.timezone)*1000)
            builder.setView(customLayout)
            val tmp = "_" + myObject.weather[0].icon
            val id = resources.getIdentifier(tmp, "drawable", "com.italkutalk.lab17")
            weather_icon.setImageResource(id)
            tv_location.text = myObject.name
            tv_content.text = "取得 API 時間: ${simpleDate1.format(timestamp_dt)}\n 天氣狀況: ${myObject.weather[0].description}\n日出: ${simpleDate2.format(timestamp_sunrise)}\n日落: ${simpleDate2.format(timestamp_sunset)}\n" +
                    "溫度: ${(myObject.main.temp-273.15).roundToLong()}(攝氏)\n體感溫度: ${(myObject.main.feels_like-273.15).roundToLong()}(攝氏)\n濕度: ${myObject.main.humidity}%\n" +
                    "風向: ${myObject.wind.deg}度\n"
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun getWeatherIcon(icon: String): Int {
        val icon_array =
            resources.obtainTypedArray(R.array.image_list) //從 R 類別讀取圖檔
        //var icon = "_$icon"
        for(i in 0 until icon_array.length()){
            if("_$icon" == icon_array.resources.getResourceEntryName(i)){
                return i
            }
        }
        return icon_array.getResourceId(0, 0)
    }
}