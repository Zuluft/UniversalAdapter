package com.zuluft.lib.universalAdapter.adapters

import androidx.annotation.IdRes
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import com.zuluft.lib.universalAdapter.viewholders.UniversalViewHolder

interface ItemClickListener<T : ItemDrawer> {

    fun <P : T> registerClickListener(
        clazz: Class<P>,
        predicate: (itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder) -> (Unit)
    )

    fun <P : T> registerClickListener(
        clazz: Class<P>, @IdRes viewId: Int,
        predicate: (itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder) -> (Unit)
    )
}