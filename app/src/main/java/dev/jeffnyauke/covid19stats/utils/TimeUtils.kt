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

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
fun getPeriod(past: Date): String {
    val now = Date()
    val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
    val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
    val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)
    val months = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

    return when {
        seconds < 60 -> {
            "Few seconds ago"
        }
        minutes < 60 -> {
            "$minutes minutes ago"
        }
        hours <= 1 -> {
            "$hours hour ${minutes % 60} min ago"
        }
        hours < 24 -> {
            "$hours hours ago"
        }
        days <= 1 -> {
            "$days day ago"
        }
        days < 10 -> {
            "$days days ago"
        }
        months <= 1 -> {
            "$months month ago"
        }
        months < 10 -> {
            "$months months ago"
        }
        else -> {
            SimpleDateFormat("dd/MM/yy").format(past).toString()
        }
    }
}

fun getPeriodWorldNews(past: String): String {
    val pastDate = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse(past)
    return getPeriod(pastDate)
}

fun getPeriodWhoNews(past: String): String {
    val pastDate = SimpleDateFormat("dd MMMM yyyy").parse(past)
    return getPeriod(pastDate)
}
