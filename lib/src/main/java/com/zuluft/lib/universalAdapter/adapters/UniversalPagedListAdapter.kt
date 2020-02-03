package com.zuluft.lib.universalAdapter.adapters

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.paging.PagedListAdapter
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import com.zuluft.lib.universalAdapter.viewholders.UniversalViewHolder

class UniversalPagedListAdapter<T : ItemDrawer>(
    areItemsTheSameCallback: (item1: T, item2: T) -> (Boolean),
    areContentsTheSameCallback: (item1: T, item2: T) -> (Boolean)
) : PagedListAdapter<T, UniversalViewHolder>(
    ItemCallback(
        areItemsTheSameCallback,
        areContentsTheSameCallback
    )
),
    ItemClickListener<T>,
    StickyHeaderItemsHolder {

    private val itemClickListeners =
        SparseArray<(drawer: ItemDrawer, holder: UniversalViewHolder) -> Unit>()

    private val itemViewClickListeners =
        SparseArray<SparseArray<(drawer: ItemDrawer, UniversalViewHolder) -> Unit>>()

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int): UniversalViewHolder {
        return UniversalViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UniversalViewHolder, position: Int) {
        val item = getItem(position)!!
        itemClickListeners[item.javaClass.hashCode()]?.apply {
            holder.itemView.setOnClickListener {
                invoke(item, holder)
            }
        }
        itemViewClickListeners[item.javaClass.hashCode()]?.apply {
            forEach { key, _ ->
                holder.itemView.findViewById<View>(key).setOnClickListener {
                    get(key)!!.invoke(item, holder)
                }
            }
        }
        item.draw(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.getLayoutId()
    }


    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var index = itemPosition
        do {
            if (getItem(itemPosition)!!.isSticky()) {
                return index
            }
            --index
        } while (index >= 0)
        return -1
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return getItem(headerPosition)!!.getLayoutId()
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        getItem(headerPosition)!!.bindData(header, headerPosition)
    }

    override fun isHeader(itemPosition: Int): Boolean {
        return getItem(itemPosition)!!.isSticky()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <P : T> registerClickListener(
        clazz: Class<P>,
        predicate: (itemDrawer: P, universalViewHolder: UniversalViewHolder) -> Unit
    ) {
        itemClickListeners.put(
            clazz.hashCode(),
            predicate as ((ItemDrawer, UniversalViewHolder) -> Unit)?
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <P : T> registerClickListener(
        clazz: Class<P>,
        viewId: Int,
        predicate: (itemDrawer: P, universalViewHolder: UniversalViewHolder) -> Unit
    ) {
        if (itemViewClickListeners[clazz.hashCode()] == null) {
            itemViewClickListeners.put(clazz.hashCode(), SparseArray())
        }
        itemViewClickListeners[clazz.hashCode()].put(
            viewId,
            predicate as ((ItemDrawer, UniversalViewHolder) -> Unit)?
        )
    }


}