package com.zuluft.lib.universalAdapter.adapters

import com.zuluft.lib.universalAdapter.models.ItemDrawer

interface AdapterApi<T : ItemDrawer> {

    fun insert(item: T, notify: Boolean)

    fun remove(position: Int, notify: Boolean)

    fun insertAll(items: List<T>, notify: Boolean)

    fun insertAndUpdate(items: List<T>)

    fun removeAll(notify: Boolean)

    fun updateAll(items: List<T>, detectMoves: Boolean)

    fun getItemDrawerAt(position: Int): T

    fun <P : T> findLastItemOfType(type: Class<P>): P?

    fun getAll(): List<T>

}