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
        { itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder ->
            Toast.makeText(this, "HHHH", Toast.LENGTH_SHORT).show()
        }
        universalAdapter.registerClickListener(TestItemDrawer::class.java, R.id.tvTest)
        { itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder ->
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
        }
        universalAdapter.registerClickListener(TestHeaderDrawer::class.java)
        { itemDrawer: ItemDrawer, universalViewHolder: UniversalViewHolder ->
            Toast.makeText(this, "Header", Toast.LENGTH_SHORT).show()
        }


    }
}
