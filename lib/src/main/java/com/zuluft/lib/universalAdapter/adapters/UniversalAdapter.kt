package com.zuluft.lib.universalAdapter.adapters

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import com.zuluft.lib.universalAdapter.viewholders.UniversalViewHolder

class UniversalAdapter<T : ItemDrawer> constructor(
    private val areItemsTheSameCallback: (item1: T, item2: T) -> (Boolean),
    private val areContentsTheSameCallback: (item1: T, item2: T) -> (Boolean)
) : RecyclerView.Adapter<UniversalViewHolder>(),
    AdapterApi<T>,
    ItemClickListener<T>,
    StickyHeaderItemsHolder {

    private val currentItems = mutableListOf<T>()

    private val itemClickListeners =
        SparseArray<(drawer: ItemDrawer, holder: UniversalViewHolder) -> Unit>()

    private val itemViewClickListeners =
        SparseArray<SparseArray<(drawer: ItemDrawer, UniversalViewHolder) -> Unit>>()

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int): UniversalViewHolder {
        return UniversalViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currentItems.size
    }

    override fun onBindViewHolder(holder: UniversalViewHolder, position: Int) {
        val item = currentItems[position]
        itemClickListeners[item.javaClass.hashCode()]?.apply {
            holder.itemView.setOnClickListener {
                invoke(item, holder)
            }
        }
        itemViewClickListeners[item.javaClass.hashCode()]?.apply {
            forEach { key, value ->
                holder.itemView.findViewById<View>(key).setOnClickListener {
                    get(key)!!.invoke(item, holder)
                }
            }
        }
        item.draw(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return currentItems[position].getLayoutId()
    }


    private fun diffUtilsUpdate(oldList: List<T>, newList: List<T>, detectMoves: Boolean) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilsCallback(
                oldList,
                newList,
                areItemsTheSameCallback,
                areContentsTheSameCallback
            ),
            detectMoves
        )
        diffResult.dispatchUpdatesTo(this)
        currentItems.apply {
            clear()
            addAll(newList)
        }
    }

    override fun insert(item: T, notify: Boolean) {
        currentItems.add(item)
        if (notify) {
            notifyItemInserted(currentItems.size - 1)
        }
    }

    override fun remove(position: Int, notify: Boolean) {
        currentItems.removeAt(position)
        if (notify) {
            notifyItemRemoved(position)
        }
    }

    override fun insertAll(items: List<T>, notify: Boolean) {
        currentItems.addAll(items)
        if (notify) {
            notifyItemRangeInserted(currentItems.size - items.size - 1, items.size)
        }
    }

    override fun removeAll(notify: Boolean) {
        val itemCount = itemCount
        currentItems.clear()
        if (notify) {
            notifyItemRangeRemoved(0, itemCount)
        }
    }

    override fun updateAll(items: List<T>, detectMoves: Boolean) {
        diffUtilsUpdate(currentItems, items, detectMoves)
    }


    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var index = itemPosition
        do {
            if (currentItems[index].isSticky()) {
                return index
            }
            --index
        } while (index >= 0)
        return -1
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return currentItems[headerPosition].getLayoutId()
    }

    override fun bindHeaderData(header: View, headerPosition: Int) {
        currentItems[headerPosition].bindData(header, headerPosition)
    }

    override fun isHeader(itemPosition: Int): Boolean {
        return currentItems[itemPosition].isSticky()
    }

    override fun <P : T> registerClickListener(
        clazz: Class<P>,
        predicate: (itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder) -> Unit
    ) {
        itemClickListeners.put(clazz.hashCode(), predicate)
    }

    override fun <P : T> registerClickListener(
        clazz: Class<P>,
        viewId: Int,
        predicate: (itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder) -> Unit
    ) {
        if (itemViewClickListeners[clazz.hashCode()] == null) {
            itemViewClickListeners.put(clazz.hashCode(), SparseArray())
        }
        itemViewClickListeners[clazz.hashCode()].put(viewId, predicate)
    }
}