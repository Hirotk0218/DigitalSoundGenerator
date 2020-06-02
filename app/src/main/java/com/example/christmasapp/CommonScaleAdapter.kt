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

    // region MARK: -private field
    var onItemClick: (item: SoundDto) -> Unit = {}
    // endregion

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemScaleBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_scale, parent, false)
        val vh = ViewHolder(binding)
        binding.root.setOnClickListener {
            val data = dataList[vh.adapterPosition]
            onItemClick(data)
        }
        return vh
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        if (data.isSelected) {

        }
    }

    class ViewHolder(binding: ItemScaleBinding) : RecyclerView.ViewHolder(binding.root)
}