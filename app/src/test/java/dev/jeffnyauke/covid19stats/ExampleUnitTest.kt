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

package dev.jeffnyauke.covid19stats

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi.Builder
import dev.jeffnyauke.covid19stats.model.History
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat


/**
 * Global local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val moshi = Builder().build()

        val jsonAdapter: JsonAdapter<History> = moshi.adapter(History::class.java)
        val history: History? = jsonAdapter.fromJson(
            "{\"country\":\"USA\",\"provinces\":[\"mainland\"],\"timeline\":{\"cases\":{\"3/10/20\":959,\"3/11/20\":1281,\"3/12/20\":1663,\"3/13/20\":2179,\"3/14/20\":2727,\"3/15/20\":3499,\"3/16/20\":4632,\"3/17/20\":6421,\"3/18/20\":7783,\"3/19/20\":13747,\"3/20/20\":19273,\"3/21/20\":25600,\"3/22/20\":33276,\"3/23/20\":43847,\"3/24/20\":53740,\"3/25/20\":65778,\"3/26/20\":83836,\"3/27/20\":101657,\"3/28/20\":121478,\"3/29/20\":140909,\"3/30/20\":161837,\"3/31/20\":188172,\"4/1/20\":213372,\"4/2/20\":243616,\"4/3/20\":275586,\"4/4/20\":308850,\"4/5/20\":337072,\"4/6/20\":366667,\"4/7/20\":396223,\"4/8/20\":429052},\"deaths\":{\"3/10/20\":28,\"3/11/20\":36,\"3/12/20\":40,\"3/13/20\":47,\"3/14/20\":54,\"3/15/20\":63,\"3/16/20\":85,\"3/17/20\":108,\"3/18/20\":118,\"3/19/20\":200,\"3/20/20\":244,\"3/21/20\":307,\"3/22/20\":417,\"3/23/20\":557,\"3/24/20\":706,\"3/25/20\":942,\"3/26/20\":1209,\"3/27/20\":1581,\"3/28/20\":2026,\"3/29/20\":2467,\"3/30/20\":2978,\"3/31/20\":3873,\"4/1/20\":4757,\"4/2/20\":5926,\"4/3/20\":7087,\"4/4/20\":8407,\"4/5/20\":9619,\"4/6/20\":10783,\"4/7/20\":12722,\"4/8/20\":14695},\"recovered\":{\"3/10/20\":8,\"3/11/20\":8,\"3/12/20\":12,\"3/13/20\":12,\"3/14/20\":12,\"3/15/20\":12,\"3/16/20\":17,\"3/17/20\":17,\"3/18/20\":105,\"3/19/20\":121,\"3/20/20\":147,\"3/21/20\":176,\"3/22/20\":178,\"3/23/20\":178,\"3/24/20\":348,\"3/25/20\":361,\"3/26/20\":681,\"3/27/20\":869,\"3/28/20\":1072,\"3/29/20\":2665,\"3/30/20\":5644,\"3/31/20\":7024,\"4/1/20\":8474,\"4/2/20\":9001,\"4/3/20\":9707,\"4/4/20\":14652,\"4/5/20\":17448,\"4/6/20\":19581,\"4/7/20\":21763,\"4/8/20\":23559}}}"
        )
        println(history)
        println(history?.timeline)
        history?.timeline?.cases?.entries?.forEach {
            println("On ${it.key} we had ${it.value} cases.")
        }
        assertEquals(4, 2 + 2)
    }

    @Test
    fun textTime() {
        val date =
            SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse("Mon, 20 Apr 2020 21:11:47 GMT")
        println(date)
        assertEquals(4, 2 + 2)
    }
}
