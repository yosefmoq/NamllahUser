package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.namllahuser.R
import com.app.namllahuser.data.main.slider.SliderData
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapterExample(context: Context) :
    SliderViewAdapter<SliderAdapterExample.SliderAdapterVH>() {
    private val context: Context
    private var mSliderItems: MutableList<SliderData> = ArrayList()
    fun renewItems(sliderItems: MutableList<SliderData>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderData) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider, parent,false)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(
        viewHolder: SliderAdapterVH,
        position: Int
    ) {
        val sliderItem: SliderData = mSliderItems[position]
/*
        viewHolder.textViewDescription.setText(sliderItem.description)
        viewHolder.textViewDescription.textSize = 16f
        viewHolder.textViewDescription.setTextColor(Color.WHITE)
*/
        Glide.with(viewHolder.itemView)
            .load(sliderItem.image.original)
            .fitCenter()
            .into(viewHolder.imageViewBackground)
        viewHolder.itemView.setOnClickListener({
            Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT)
                .show()

        })
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(var itemViewA: View) :
        ViewHolder(itemViewA) {
        var imageViewBackground: ImageView
        var imageGifContainer: ImageView
//        var textViewDescription: TextView

        init {
            imageViewBackground = itemViewA.findViewById(R.id.iv_auto_image_slider)
            imageGifContainer = itemViewA.findViewById(R.id.iv_gif_container)
//            textViewDescription = itemViewA.findViewById(R.id.tv_auto_image_slider)
        }
    }

    init {
        this.context = context
    }
}
