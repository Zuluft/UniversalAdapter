package com.zuluft.universal_adapter_sample

import android.annotation.SuppressLint
import android.view.View
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import kotlinx.android.synthetic.main.item_header.view.*

class TestHeaderDrawer : ItemDrawer() {
    @SuppressLint("SetTextI18n")
    override fun bindData(itemView: View, position: Int) {
        itemView.tvTitle.text = "title $position"
    }

    override fun getLayoutId(): Int {
        return R.layout.item_header
    }

    override fun isSticky(): Boolean {
        return true
    }
}