package com.wthrnrslt.lightweathernursultan

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var weather_url = ""
    var api_id = "fd648c1cdb014e759086abea81e377dc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weather_url =
            "https://api.weatherbit.io/v2.0/current?" + "lat=" + (51.16) + "&lon=" + (71.44) + "&key=" + api_id
        getData()
    }

    @SuppressLint("SetTextI18n")
    fun getData (){
        val queue = Volley.newRequestQueue(this)
        Log.e("lat", weather_url)


        val stringRequest = StringRequest(
            Request.Method.GET, weather_url,
            { response ->
                Log.e("lat", response.toString())
                val obj = JSONObject(response)

                val arr = obj.getJSONArray("data")
                Log.e("lat obj1", arr.toString())

                val obj2 = arr.getJSONObject(0)
                Log.e("lat obj2", obj2.toString())

                temperatureText.text = obj2.getString("temp") + " °C"
                conditions.text = "Feels like: "+obj2.getString("app_temp") + " °C"
                locationText.text = obj2.getString("city_name")
                pressure_tv.text = obj2.getString("pres") + " mb"
                wind_tv.text = obj2.getString("wind_spd") + " m/s"
                humidity_tv.text = obj2.getString("rh") + " %"

                if(obj2.getString("temp").toFloat() < 0) {
                    imageView.setImageResource(R.drawable.cold)
                } else if(obj2.getString("temp").toFloat() > 0 && obj2.getString("temp").toFloat()< 25 ) {
                    imageView.setImageResource(R.drawable.warm)
                } else {
                    imageView.setImageResource(R.drawable.hot)
                }
            }
            , {
                Toast.makeText(this, "Произошла ошибка при загрузке данных. Попробуйте еще раз.", Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }
}