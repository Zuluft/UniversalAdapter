package com.zuluft.universal_adapter_sample

import android.view.View
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import kotlinx.android.synthetic.main.item_test.view.*

class TestItemDrawer(val text: String) :
    ItemDrawer() {
    override fun bindData(itemView: View, position: Int) {
        itemView.tvTest.text = text
    }

    override fun getLayoutId(): Int {
        return R.layout.item_test
    }

    override fun isSticky(): Boolean {
        return false
    }
}