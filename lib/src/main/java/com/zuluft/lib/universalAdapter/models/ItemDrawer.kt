package com.zuluft.lib.universalAdapter.models

import android.view.View
import androidx.annotation.LayoutRes
import com.zuluft.lib.universalAdapter.viewholders.UniversalViewHolder

abstract class ItemDrawer {

    fun draw(vh: UniversalViewHolder) {
        bindData(vh.itemView, vh.adapterPosition)
    }

    abstract fun bindData(itemView: View, position: Int)

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun isSticky(): Boolean

}