package com.example.natifegif.presentation.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.natifegif.R
import com.example.natifegif.databinding.GifItemBinding
import com.example.natifegif.data.database.GifData
import java.io.File


class RcAdapter(private val listener: Listener, private val context: Context) :
    ListAdapter<GifData, RcAdapter.MyHolder>(ItemComparator()) {
    class MyHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun setData(gifData: GifData, listener: Listener, context: Context) {
            val binding = GifItemBinding.bind(view)
            with(binding){
                Glide.with(context).asGif()
                    .load(Uri.fromFile(File(gifData.url!!))).into(mainImage)

                tvTitle.text = gifData.title
                mainImage.setOnClickListener {
                    listener.onClickItem(gifData)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        getItem(position)?.let { holder.setData(it, listener, context) }


    }

    interface Listener {
        fun onClickItem(gifData: GifData)

    }

    class ItemComparator : DiffUtil.ItemCallback<GifData>() {
        override fun areItemsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifData, newItem: GifData): Boolean {
            return oldItem == newItem
        }
    }


}