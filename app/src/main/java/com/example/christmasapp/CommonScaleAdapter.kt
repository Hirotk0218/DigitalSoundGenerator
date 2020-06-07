package com.example.christmasapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.christmasapp.databinding.ItemScaleBinding

/**
 * 共用Adapter
 */
class CommonScaleAdapter : ListAdapter<SoundDto, CommonScaleAdapter.ViewHolder>(ITEM_COMPARATOR) {

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<SoundDto>() {
            override fun areItemsTheSame(oldItem: SoundDto, newItem: SoundDto): Boolean =
                oldItem.sound.contentEquals(newItem.sound)

            override fun areContentsTheSame(oldItem: SoundDto, newItem: SoundDto): Boolean =
                oldItem == newItem
        }
    }

    // region MARK: -public field
    var onItemClick: (position: Int) -> Unit = {}
    // endregion

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemScaleBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_scale, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        val binding = holder.binding
        binding.itemView.setOnClickListener {
            onItemClick(position)

            if (data.isSelected) {
                binding.itemView.setBackgroundColor(binding.itemView.context.getColor(R.color.colorAccent))
            } else {
                binding.itemView.setBackgroundColor(binding.itemView.context.getColor(R.color.colorPrimaryDark))
            }
        }

        if (data.isSelected) {
            binding.itemView.setBackgroundColor(binding.itemView.context.getColor(R.color.colorAccent))
        } else {
            binding.itemView.setBackgroundColor(binding.itemView.context.getColor(R.color.colorPrimaryDark))
        }
    }

    class ViewHolder(val binding: ItemScaleBinding) : RecyclerView.ViewHolder(binding.root)
}