package com.nicolas.checkplant.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nicolas.checkplant.databinding.LayoutItemsPlantBinding
import com.nicolas.checkplant.data.model.Plant

class AdapterPlant(
    private val listPlants: List<Plant>,
    private val clickPlant: ((plant: Plant) -> Unit)
) : RecyclerView.Adapter<AdapterPlant.MainViewHolder>() {

    class MainViewHolder(
        binding: LayoutItemsPlantBinding,
        private val clickPlant: ((plant: Plant) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvNamePlant
        private val description = binding.tvDescriptionPlant
        private val image = binding.imgPlant
        private val dayAndMont = binding.tvDayPlant

        @SuppressLint("SetTextI18n")
        fun bind(plant: Plant) {

            title.text = plant.name
            description.text = plant.description
            dayAndMont.text = "${plant.day}/${plant.month}"

            loadImagePlant(image, plant.backgroundImage)

            itemView.setOnClickListener {
                clickPlant.invoke(plant)
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
        val layout =
            LayoutItemsPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MainViewHolder(layout, clickPlant)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listPlants[position])
    }

    override fun getItemCount() = listPlants.size
}