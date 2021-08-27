package com.example.ubox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.ubox.databinding.SlideItemContainerBinding
import com.example.ubox.model.FilemsMain

class SliderAdapter (private val homeFilms : FilemsMain , val viewPager2:ViewPager2)
    : RecyclerView.Adapter<SliderAdapter.SlidViewHolder>() {

    class SlidViewHolder(var view: SlideItemContainerBinding? , ): RecyclerView.ViewHolder(view?.root!!)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlidViewHolder {
        val view: SlideItemContainerBinding =
            SlideItemContainerBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return SlidViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlidViewHolder, position: Int) {
//        holder.setImage(listItems.get(position))

        val filem = homeFilms.data[position]
        var types = ""

        filem.video_genres.forEach {
            types = "$it , $types"
        }.toString()

        holder.view!!.apply {
            typeFilem.text = types.subSequence(0, (types.length - 3))
            nameFilem.text = filem.video.name
            textStar.text = filem.video.imdbrate
        }
        if(position == homeFilms.data.size - 2){
            viewPager2.post(runnable)
        }

        Glide.with(holder.itemView.context)
            .load("https://cdn-edge01.scopesky.iq/share/uploads/slider/imgs/${filem.image}")
            .into(holder.view!!.imgForSlide)
    }

    override fun getItemCount(): Int {
        return homeFilms.data.size
    }

    private var runnable = Runnable {
        homeFilms.data.addAll(homeFilms.data)
        notifyDataSetChanged()
    }

}