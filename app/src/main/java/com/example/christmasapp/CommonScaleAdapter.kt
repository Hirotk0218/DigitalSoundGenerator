package com.example.christmasapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.christmasapp.databinding.ItemScaleBinding

/**
 * 共用Adapter
 *
 * @param dataList 音データ
 */
class CommonScaleAdapter(
    private val dataList: List<SoundDto>
) : RecyclerView.Adapter<CommonScaleAdapter.ViewHolder>() {

    // region MARK: -public field
    var onItemClick: (position: Int) -> Unit = {}
    // endregion

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemScaleBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_scale, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

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