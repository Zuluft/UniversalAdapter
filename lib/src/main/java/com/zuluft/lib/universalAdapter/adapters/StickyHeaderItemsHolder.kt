package com.zuluft.lib.universalAdapter.adapters

import android.view.View

interface StickyHeaderItemsHolder {
    fun getHeaderPositionForItem(itemPosition: Int): Int

    fun getHeaderLayout(headerPosition: Int): Int

    fun bindHeaderData(header: View, headerPosition: Int)

    fun isHeader(itemPosition: Int): Boolean
}