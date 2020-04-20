/*
 *
 *  Copyright (c) 2020 jeffnyauke@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
