package com.zuluft.universal_adapter_sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zuluft.lib.universalAdapter.builders.UniversalAdapterBuilder
import com.zuluft.lib.universalAdapter.models.ItemDrawer
import com.zuluft.lib.universalAdapter.viewholders.UniversalViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val universalAdapter =
            UniversalAdapterBuilder.of(ItemDrawer::class.java)
                .setAreContentsTheSameCallback { _, _ ->
                    false
                }
                .setAreItemsTheSameCallback { _, _ ->
                    false
                }
                .buildAdapterWith(recyclerView)
        universalAdapter.insert(TestHeaderDrawer(), false)
        universalAdapter.insert(TestItemDrawer("zzzzz0"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz1"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz2"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestHeaderDrawer(), false)
        universalAdapter.insert(TestItemDrawer("zzzzz0"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz1"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz2"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestHeaderDrawer(), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.insert(TestItemDrawer("zzzzz3"), false)
        universalAdapter.registerClickListener(TestItemDrawer::class.java)
        { testItemDrawer: TestItemDrawer, _: UniversalViewHolder ->
            Toast.makeText(this, testItemDrawer.text, Toast.LENGTH_SHORT).show()
        }

    }
}
