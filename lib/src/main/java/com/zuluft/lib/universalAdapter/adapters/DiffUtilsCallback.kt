package com.zuluft.lib.universalAdapter.adapters

import androidx.recyclerview.widget.DiffUtil
import com.zuluft.lib.universalAdapter.models.ItemDrawer

class DiffUtilsCallback<T : ItemDrawer>
constructor(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val areItemsTheSameCallback: (item1: T, item2: T) -> (Boolean),
    private val areContentsTheSameCallback: (item1: T, item2: T) -> (Boolean)
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSameCallback.invoke(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSameCallback.invoke(
            oldList[oldItemPosition],
            newList[newItemPosition]
        )
    }
}