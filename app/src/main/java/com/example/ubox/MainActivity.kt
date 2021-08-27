package com.example.ubox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.ubox.databinding.ActivityMainBinding
import com.example.ubox.model.FilemsMain
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val client = OkHttpClient()

    lateinit var viewPager2:ViewPager2
    private var sliderHandler: Handler = Handler()

    var listAdapterItem:MutableList<FilemsMain> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = findViewById(R.id.viewPagerImgSlider)

        makeRequestByUsingOKHTTP()
        initViewPager ()
    }
    private fun makeRequestByUsingOKHTTP() {
        Log.i("resp", "get it")

        val request = Request.Builder()
            .url("https://web.uboxhd.com/new_panel/api/web/slider?type=home")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("resp", "${e.message}")

                runOnUiThread {
                    Toast.makeText(applicationContext, "Connection failed....", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val result = GsonBuilder().create()
                    val homeFilmsResult = result.fromJson(body, FilemsMain::class.java)
                    runOnUiThread {
                        Log.i("resp" ,"this: ${homeFilmsResult}")
                        binding.viewPagerImgSlider.adapter = SliderAdapter(homeFilmsResult , viewPager2)
                    }
            }

        })
    }

    private fun initViewPager () = viewPager2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(70))
                addTransformer { page, position ->
                    var r = 1 - Math.abs(position) + 0.9
                    page.scaleY = (0.85f + r * 0.1f).toFloat()
                }
            })
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 5000)
                }
            })
        }

    private var sliderRunnable:Runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable,5000 )
    }

}