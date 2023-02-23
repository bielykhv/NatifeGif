package com.example.natifegif.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.natifegif.data.database.GifData
import com.example.natifegif.databinding.ActivityGifBinding
import java.lang.Math.abs
import javax.inject.Inject


class GifActivity : AppCompatActivity() {
    private var id: String = ""
    private var index: Int = 0
    private var index1: Int = -1
    private var list = mutableListOf<GifData>()
    private val binding by lazy {
        ActivityGifBinding.inflate(layoutInflater)
    }
    private lateinit var model: ActivityViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        model = ViewModelProvider(this, viewModelFactory)[ActivityViewModel::class.java]
        id = intent.getStringExtra("id").toString()
        model.gifList.observe(this) {
            if (index1 == -1) {
                for (item in it) {
                    if (item.id == id) {
                        index = it.indexOf(item)
                    }
                    list.add(item)
                }
                Glide.with(this).asGif()
                    .load(it[index].url).into(binding.imageView)
                index1 = index
            } else {
                Glide.with(this).asGif()
                    .load(it[index1].url).into(binding.imageView)
            }
        }
        onSwipe()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        index1 = savedInstanceState.getInt("index")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("index", index1)
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onSwipe() {
        binding.imageView.setOnTouchListener(
            object : View.OnTouchListener {
                private val gestureDetector = GestureDetector(this@GifActivity, GestureListener())
                override fun onTouch(view: View, event: MotionEvent): Boolean {
                    return gestureDetector.onTouchEvent(event)
                }
                inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
                    private val SWIPE_THRESHOLD = 100
                    private val SWIPE_VELOCITY_THRESHOLD = 100
                    override fun onDown(e: MotionEvent): Boolean {
                        return true
                    }
                    override fun onFling(
                        e1: MotionEvent,
                        e2: MotionEvent,
                        velocityX: Float,
                        velocityY: Float,
                    ): Boolean {
                        val diffX = e2.x - e1.x
                        val diffY = e2.y - e1.y
                        if (abs(diffX) > abs(diffY) && abs(diffX) > SWIPE_THRESHOLD
                            && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                if (index1 == 0) index1 = list.size
                                --index1
                                Glide.with(this@GifActivity).asGif()
                                    .load(list[index1].url).into(binding.imageView)
                            } else {
                                if (index1 == list.size - 1) {
                                    index1 = 0
                                } else ++index1
                                Glide.with(this@GifActivity).asGif()
                                    .load(list[index1].url).into(binding.imageView)
                            }
                            return true
                        }
                        return false
                    }
                }
            }
        )
    }
}
