package com.nicolas.checkplant.common

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.databinding.LayoutProgressPlantBinding

class AdapterProgressPlant(
    private val imagePlantList: List<ImagePlant>
) : RecyclerView.Adapter<AdapterProgressPlant.MainViewHolder>() {

    class MainViewHolder(binding: LayoutProgressPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val image = binding.imgProgressPlant

        fun bind(imagePlant: ImagePlant) {
            loadImagePlant(image, imagePlant.imageUri)
        }

        private fun loadImagePlant(imageView: ImageView, uriImage: String) {
            uriImage.let {
                Glide.with(imageView.context)
                    .load(uriImage)
                    .centerCrop()
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layout = LayoutProgressPlantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(imagePlantList[position])
    }

    override fun getItemCount() = imagePlantList.size

}