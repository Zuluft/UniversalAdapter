package com.zuluft.lib.universalAdapter.decorators

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zuluft.lib.universalAdapter.adapters.StickyHeaderItemsHolder

class StickyHeaderItemDecorator(private val stickyHeaderItemsHolder: StickyHeaderItemsHolder) :
    RecyclerView.ItemDecoration() {

    private var currentHeaderHeight: Int = 0

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }
        val headerPosition = stickyHeaderItemsHolder.getHeaderPositionForItem(topChildPosition)
        if (headerPosition == -1) {
            return
        }
        val headerView = getHeaderViewForPosition(headerPosition, parent)
        fixLayoutSize(parent, headerView)
        val childInContact = getChildInContact(parent, headerView.bottom, headerPosition)
        if (childInContact != null && stickyHeaderItemsHolder.isHeader(
                parent.getChildAdapterPosition(
                    childInContact
                )
            )
        ) {
            moveHeader(c, headerView, childInContact)
        } else {
            drawHeader(c, headerView)
        }
    }

    private fun getHeaderViewForPosition(position: Int, parent: RecyclerView): View {
        return LayoutInflater.from(parent.context)
            .inflate(stickyHeaderItemsHolder.getHeaderLayout(position), parent, false)
            .apply {
                stickyHeaderItemsHolder.bindHeaderData(this, position)
            }
    }

    private fun drawHeader(c: Canvas, currentHeader: View) {
        c.save()
        c.translate(0f, 0f)
        currentHeader.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, childInContact: View) {
        c.save()
        c.translate(0f, (childInContact.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(
        parent: RecyclerView,
        contactPoint: Int,
        headerPosition: Int
    ): View? {
        for (i in 0 until parent.childCount) {
            var heightTolerance = 0
            val child = parent.getChildAt(i)
            if (headerPosition != i && stickyHeaderItemsHolder.isHeader(
                    parent.getChildAdapterPosition(
                        child
                    )
                )
            ) {
                heightTolerance = currentHeaderHeight - child.height
            }
            val childBottom = if (child.top > 0) {
                child.bottom + heightTolerance
            } else {
                child.bottom
            }
            if (childBottom > contactPoint && child.top <= contactPoint) {
                return child
            }
        }
        return null
    }

    private fun fixLayoutSize(parent: RecyclerView, child: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
        val childWidthSpec =
            ViewGroup.getChildMeasureSpec(
                widthSpec,
                child.paddingStart + child.paddingEnd,
                child.layoutParams.width
            )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            child.paddingTop + child.paddingBottom,
            child.layoutParams.height
        )
        child.measure(childWidthSpec, childHeightSpec)
        currentHeaderHeight = child.measuredHeight
        child.layout(0, 0, child.measuredWidth, currentHeaderHeight)
    }
}