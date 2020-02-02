package com.zuluft.lib.universalAdapter.adapters

import androidx.recyclerview.widget.DiffUtil
import com.zuluft.lib.universalAdapter.models.ItemDrawer

class ItemCallback<T : ItemDrawer>
constructor(
    private val areItemsTheSameCallback: (item1: T, item2: T) -> (Boolean),
    private val areContentsTheSameCallback: (item1: T, item2: T) -> (Boolean)
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsTheSameCallback.invoke(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsTheSameCallback.invoke(oldItem, newItem)
    }
}