package com.example.natifegif.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.natifegif.data.database.GifData
import com.example.natifegif.databinding.ActivityGifBinding
import com.example.natifegif.utils.OnSwipeTouchListener
import javax.inject.Inject


class GifActivity : AppCompatActivity() {
    private var onSwipeTouchListener: OnSwipeTouchListener? = null
    private var id: String = ""
    private var index: Int = 0
    private var index1: Int = -1
    var list = mutableListOf<GifData>()
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
        onSwipeTouchListener = OnSwipeTouchListener(binding.imageView, this)
        model.getGifList()
        id = intent.getStringExtra("id")!!
        model.gifList.observe(this) {
            if (index1 == -1){
                for (item in it) {
                    if (item.id == id) {
                        index = it.indexOf(item)

                    }
                    list.add(item)
                }
                Glide.with(this).asGif()
                    .load(it[index].url).into(binding.imageView)
                index1 = index
            }else{

                Glide.with(this).asGif()
                    .load(it[index1].url).into(binding.imageView)
            }

        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        index1 = savedInstanceState.getInt("index")
        Log.d("MyLog", index1.toString())
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("index", index1)
        Log.d("MyLog", index1.toString())
        super.onSaveInstanceState(outState)

    }

    fun swipeLeft() {
        if (index1 == list.size-1){
            index1 = 0
        }else ++index1
        Glide.with(this).asGif()
            .load(list[index1].url).into(binding.imageView)
          }

    fun swipeRight() {
        if (index1 == 0) index1= list.size
        --index1
        Glide.with(this).asGif()
            .load(list[index1].url).into(binding.imageView)

    }

}
