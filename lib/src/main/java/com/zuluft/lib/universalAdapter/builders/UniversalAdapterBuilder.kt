package com.zuluft.lib.universalAdapter.builders

import androidx.recyclerview.widget.RecyclerView
import com.zuluft.lib.universalAdapter.adapters.StickyHeaderItemsHolder
import com.zuluft.lib.universalAdapter.adapters.UniversalAdapter
import com.zuluft.lib.universalAdapter.adapters.UniversalPagedListAdapter
import com.zuluft.lib.universalAdapter.decorators.StickyHeaderItemDecorator
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import java.lang.RuntimeException

class UniversalAdapterBuilder<T : ItemDrawer> {

    private var areItemsTheSameCallback: ((item1: T, item2: T) -> (Boolean))? = null
    private var areContentsTheSameCallback: ((item1: T, item2: T) -> (Boolean))? = null


    companion object {
        fun <P : ItemDrawer> of(clazz: Class<P>): UniversalAdapterBuilder<P> {
            return UniversalAdapterBuilder()
        }
    }

    fun setAreItemsTheSameCallback(areItemsTheSameCallback: ((item1: T, item2: T) -> (Boolean))):
            UniversalAdapterBuilder<T> {
        this.areItemsTheSameCallback = areItemsTheSameCallback
        return this
    }

    fun setAreContentsTheSameCallback(areContentsTheSameCallback: ((item1: T, item2: T) -> (Boolean))):
            UniversalAdapterBuilder<T> {
        this.areContentsTheSameCallback = areContentsTheSameCallback
        return this
    }

    private fun checkCallbacks() {
        if (areItemsTheSameCallback == null) {
            throw RuntimeException("set areItemsTheSameCallback before")
        }
        if (areContentsTheSameCallback == null) {
            throw RuntimeException("set areContentsTheSameCallback before")
        }
    }

    private fun getStickyHeaderItemDecorator(stickyHeaderItemsHolder: StickyHeaderItemsHolder): StickyHeaderItemDecorator {
        return StickyHeaderItemDecorator(stickyHeaderItemsHolder)
    }

    fun buildAdapterWith(recyclerView: RecyclerView): UniversalAdapter<T> {
        checkCallbacks()
        val adapter = UniversalAdapter(areItemsTheSameCallback!!, areContentsTheSameCallback!!)
        val itemDecorator = getStickyHeaderItemDecorator(adapter)
        recyclerView.addItemDecoration(itemDecorator)
        recyclerView.adapter = adapter
        return adapter
    }

    fun buildPagedListAdapterWith(recyclerView: RecyclerView): UniversalPagedListAdapter<T> {
        checkCallbacks()
        val adapter =
            UniversalPagedListAdapter(areItemsTheSameCallback!!, areContentsTheSameCallback!!)
        val itemDecorator = getStickyHeaderItemDecorator(adapter)
        recyclerView.addItemDecoration(itemDecorator)
        recyclerView.adapter = adapter
        return adapter
    }


}