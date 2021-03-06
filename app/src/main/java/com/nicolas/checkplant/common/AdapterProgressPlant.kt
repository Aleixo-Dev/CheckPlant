package com.nicolas.checkplant.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.databinding.LayoutProgressPlantBinding

class AdapterProgressPlant(
    private val imagePlantList: List<ImagePlant>,
    private val clickImagePlant: ((imagePlant: ImagePlant) -> Unit)
) : RecyclerView.Adapter<AdapterProgressPlant.MainViewHolder>() {

    class MainViewHolder(
        binding: LayoutProgressPlantBinding,
        private val clickImagePlant: ((imagePlant: ImagePlant) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        private val image = binding.imgProgressPlant
        private val progressDayMont = binding.progressDayMonth

        @SuppressLint("SetTextI18n")
        fun bind(imagePlant: ImagePlant) {
            loadImagePlant(image, imagePlant.imageUri)
            progressDayMont.text = "${imagePlant.day}/${imagePlant.month}"

            itemView.setOnClickListener {
                clickImagePlant.invoke(imagePlant)
            }
        }

        private fun loadImagePlant(imageView: ImageView, uriImage: String) {
            uriImage.let {
                Glide.with(imageView.context)
                    .load(uriImage)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
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
        return MainViewHolder(layout, clickImagePlant)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(imagePlantList[position])
    }

    override fun getItemCount() = imagePlantList.size

}