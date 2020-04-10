package dev.jeffnyauke.covid19stats.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun getFormattedNumber(amount: Int): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(amount);
}

fun getFormattedNumber(amount: Double): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(df.format(amount).toDouble());
}
