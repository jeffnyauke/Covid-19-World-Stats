package dev.jeffnyauke.covid19stats.utils

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.marker_view.view.*

class CustomMarker(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        val value = entry?.y?.toInt() ?: 0
        var resText = ""
        resText = if (getFormattedNumber(value).length > 8) {
            "Cases: " + getFormattedNumber(value).substring(0, 7)
        } else {
            "Cases: ${getFormattedNumber(value)}"
        }
        tvPrice.text = resText
        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height - 10f)
    }
}