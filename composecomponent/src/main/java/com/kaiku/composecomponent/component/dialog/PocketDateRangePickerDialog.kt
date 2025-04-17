package com.kaiku.composecomponent.component.dialog

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.LocalProvider.LocalDebugTag
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.component.picker.PocketCircularRoller
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.debug
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Calendar

/**
 * DatePickerState
 * @property year 年
 * @property month 月
 * @property day 日
 */
data class DatePickerState(
    val year: Int,
    val month: Int,
    val day: Int
) {

    fun toCalendar(): Calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)
    }

    fun toText() ="$year/$month/$day"

    companion object {
        val MONTHS = 1..12
        val DAYS = 1..31
    }
}

sealed class DatePickerEvent {
    data object Valid : DatePickerEvent()
    data class Error(val msg: String) : DatePickerEvent()
    data object UnKnown : DatePickerEvent()
}

/**
 * PocketDateRangePickerDialog 起訖日區間選擇
 * @param itemHeight 單一拉霸元件高度
 * @param start 起始日期
 * @param end 結束日期
 * @param min 日期區間最小值
 * @param max 日期區間最大值
 * @param onNewStartForceEndChanged export 使用者選擇新的起始日期，強制更新結束日期
 * @param onNewEndForceStartChanged export 使用者選擇新的結束日期，強制更新起始日期
 * @param onValidation export 檢查按下確定後的合法性
 * @param onConfirm export 確定點擊
 * @param onCancellation export 取消點擊事件
 */
@Composable
fun PocketDateRangePickerDialog(
    modifier: Modifier = Modifier,
    itemHeight: Dp = 40.sdp(),
    start: DatePickerState = DatePickerState(2022, 10, 5),
    end: DatePickerState = DatePickerState(2025, 3, 11),
    min: DatePickerState = start,
    max: DatePickerState = end,
    onNewStartForceEndChanged: ((newStart: DatePickerState) -> DatePickerState?)? = { null },
    onNewEndForceStartChanged: ((newEnd: DatePickerState) -> DatePickerState?)? = { null },
    onValidation: (Pair<DatePickerState, DatePickerState>) -> DatePickerEvent = { DatePickerEvent.Valid },
    onConfirm: (Pair<DatePickerState, DatePickerState>) -> Unit,
    onCancellation: () -> Unit
) {
    val tag = LocalDebugTag.current
    val context = LocalContext.current
    var isStartInit by remember { mutableStateOf(false) }
    var isEndInit by remember { mutableStateOf(false) }

    // 滾動狀態追踪
    var isStartYearScrolling by remember { mutableStateOf(false) }
    var isStartMonthScrolling by remember { mutableStateOf(false) }
    var isStartDayScrolling by remember { mutableStateOf(false) }
    var isEndYearScrolling by remember { mutableStateOf(false) }
    var isEndMonthScrolling by remember { mutableStateOf(false) }
    var isEndDayScrolling by remember { mutableStateOf(false) }

    // TODO: 先用這種方式解決
    val isScrolling by remember(
        isStartYearScrolling,
        isStartMonthScrolling,
        isStartDayScrolling,
        isEndYearScrolling,
        isEndMonthScrolling,
        isEndDayScrolling
    ) {
        derivedStateOf {
            listOf(
                isStartYearScrolling,
                isStartMonthScrolling,
                isStartDayScrolling,
                isEndYearScrolling,
                isEndMonthScrolling,
                isEndDayScrolling
            ).any { it }
        }
    }

    var startDay by remember { mutableIntStateOf(start.day) }
    var startMonth by remember { mutableIntStateOf(start.month) }
    var startYear by remember { mutableIntStateOf(start.year) }
    var startLastDayInMonth by remember { mutableIntStateOf(31) }

    var endDay by remember { mutableIntStateOf(end.day) }
    var endMonth by remember { mutableIntStateOf(end.month) }
    var endYear by remember { mutableIntStateOf(end.year) }
    var endLastDayInMonth by remember { mutableIntStateOf(31) }

    LaunchedEffect(startYear, startMonth, startDay) {
        if (!isStartInit) {
            isStartInit = true
        } else {
            snapshotFlow { Triple(startYear, startMonth, startDay) }
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { (year, month, day) ->
                    tag.debug("起始:$year/$month/$day")
                    val newStart = DatePickerState(year, month, day)
                    onNewStartForceEndChanged?.invoke(newStart)?.let { newForceEnd ->
                        endYear = newForceEnd.year
                        endMonth = newForceEnd.month
                        endDay = newForceEnd.day
                    }
                }
        }
    }

    LaunchedEffect(endYear, endMonth, endDay) {
        if (!isEndInit) {
            isEndInit = true
        } else {
            snapshotFlow { Triple(endYear, endMonth, endDay) }
                .debounce(300)
                .collectLatest { (year, month, day) ->
                    tag.debug("結束:$year/$month/$day")
                    val newEnd = DatePickerState(year, month, day)
                    onNewEndForceStartChanged?.invoke(newEnd)?.let { newForceStart ->
                        endYear = newForceStart.year
                        endMonth = newForceStart.month
                        endDay = newForceStart.day
                    }
                }
        }
    }

    PocketComposeDialog(
        modifier = modifier,
        title = "日期篩選",
        buttonPaddingBetween = 17.sdp(),
        positiveText = "確認",
        negativeText = "取消",
        onPositiveClick = {
            if(!isScrolling) {
                tag.debug("起始:$startYear/$startMonth/$startDay")
                tag.debug("結束:$endYear/$endMonth/$endDay")
                val outputStart = DatePickerState(startYear, startMonth, startDay)
                val outputEnd = DatePickerState(endYear, endMonth, endDay)

                //檢查例外情況
                when (val event = onValidation.invoke(outputStart to outputEnd)) {
                    is DatePickerEvent.Valid -> {
                        onConfirm.invoke(
                            outputStart to outputEnd
                        )
                    }

                    is DatePickerEvent.Error -> {
                        Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).apply { show() }
                    }

                    else -> Unit
                }
            }
        },
        onNegativeClick = onCancellation,
        content = {
            PocketSpacer(height = 12)
            PocketText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                config = PocketTextConfig(
                    value = "起始時間",
                    style = text15Sp(500)
                )
            )

            PocketSpacer(height = 20)

            Row {
                PocketCircularRoller(
                    width = 60.sdp(),
                    itemHeight = itemHeight,
                    items = rememberYearLimitation(min, max),
                    initialItem = startYear,
                    isInfinite = false,
                    onScrollStateChanged = {
                        isStartYearScrolling = it
                    },
                    displayText = { _, item ->
                        item?.let { "${it}年" }.orEmpty()
                    },
                    onItemSelected = { _, item ->
                        startYear = item
                        val (lastDay, day, invalid) = adjustDay(
                            month = startMonth,
                            year = startYear,
                            lastDayInMonth = startLastDayInMonth,
                            day = startDay
                        )

                        lastDay?.let { startLastDayInMonth = it }
                        day?.let { startDay = it }
                    }
                )
                PocketSpacer(width = 40)
                PocketCircularRoller(
                    width = 80.sdp(),
                    itemHeight = itemHeight,
                    items = DatePickerState.MONTHS.toList(),
                    initialItem = startMonth,
                    isEnable = rememberMonthEnables(
                        current = DatePickerState(
                            year = startYear,
                            month = startMonth,
                            day = startDay
                        ),
                        min = min,
                        max = max
                    ),
                    selectedTextStyle = text17Sp(600),
                    unSelectedTextStyle = text15Sp(400),
                    selectedTextColor = Color.White,
                    unSelectedTextColor = color_717071,
                    onScrollStateChanged = {
                        isStartMonthScrolling = it
                    },
                    displayText = { _, item ->
                        item?.let { "${it}月" }.orEmpty()
                    },
                    onItemSelected = { _, item ->
                        startMonth = item
                        val (lastDay, day, invalid) = adjustDay(
                            month = startMonth,
                            year = startYear,
                            lastDayInMonth = startLastDayInMonth,
                            day = startDay
                        )
                        lastDay?.let { startLastDayInMonth = it }
                        day?.let { startDay = it }
                    }
                )
                PocketSpacer(width = 40)
                PocketCircularRoller(
                    width = 60.sdp(),
                    itemHeight = itemHeight,
                    items = DatePickerState.DAYS.toList(),
                    initialItem = startDay,
                    isEnable = rememberDayEnables(
                        current = DatePickerState(
                            year = startYear,
                            month = startMonth,
                            day = startDay
                        ),
                        min = min,
                        max = max,
                        lastDayInMonth = startLastDayInMonth
                    ),
                    onScrollStateChanged = {
                        isStartDayScrolling = it
                    },
                    displayText = { _, item ->
                        item?.let { "${it}日" }.orEmpty()
                    },
                    onItemSelected = { _, item ->
                        startDay = item
                    }
                )
            }
            PocketSpacer(height = 20)
            HorizontalDivider(
                thickness = 1.sdp(),
                color = color_414141
            )
            PocketSpacer(height = 20)

            // ----------------------------- 分隔線 ----------------------------- //

            PocketText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                config = PocketTextConfig(
                    value = "結束時間",
                    style = text15Sp(500)
                )
            )
            PocketSpacer(height = 20)
            Row {
                PocketCircularRoller(
                    width = 60.sdp(),
                    itemHeight = itemHeight,
                    items = rememberYearLimitation(min, max),
                    initialItem = endYear,
                    isInfinite = false,
                    onScrollStateChanged = {
                        isEndYearScrolling = it
                    },
                    displayText = { _, item ->
                        item?.let { "${it}年" }.orEmpty()
                    },
                    onItemSelected = { _, item ->
                        endYear = item
                        val (lastDay, day, invalid) = adjustDay(
                            month = endMonth,
                            year = endYear,
                            lastDayInMonth = endLastDayInMonth,
                            day = endDay
                        )
                        lastDay?.let { endLastDayInMonth = it }
                        day?.let { endDay = it }
                    }
                )
                PocketSpacer(width = 40)
                PocketCircularRoller(
                    width = 80.sdp(),
                    itemHeight = itemHeight,
                    items = DatePickerState.MONTHS.toMutableList(),
                    initialItem = endMonth,
                    isEnable = rememberMonthEnables(
                        current = DatePickerState(
                            year = endYear,
                            month = endMonth,
                            day = endDay
                        ),
                        min = min,
                        max = max
                    ),
                    onScrollStateChanged = {
                        isEndMonthScrolling = it
                    },
                    displayText = { _, item ->
                        item?.let { "${it}月" }.orEmpty()
                    },
                    onItemSelected = { _, item ->
                        endMonth = item
                        val (lastDay, day, invalid) = adjustDay(
                            month = endMonth,
                            year = endYear,
                            lastDayInMonth = endLastDayInMonth,
                            day = endDay
                        )
                        lastDay?.let { endLastDayInMonth = it }
                        day?.let { endDay = it }
                    }
                )
                PocketSpacer(width = 40)
                PocketCircularRoller(
                    width = 60.sdp(),
                    itemHeight = itemHeight,
                    items = DatePickerState.DAYS.toList(),
                    initialItem = endDay,
                    isEnable = rememberDayEnables(
                        current = DatePickerState(
                            year = endYear,
                            month = endMonth,
                            day = endDay
                        ),
                        min = min,
                        max = max,
                        lastDayInMonth = endLastDayInMonth
                    ),
                    onScrollStateChanged = {
                        isEndDayScrolling = it
                    },
                    displayText = { _, item ->
                        item?.let { "${it}日" }.orEmpty()
                    },
                    onItemSelected = { _, item ->
                        endDay = item
                    }
                )
            }
            PocketSpacer(height = 40)
        }
    )

}

@Composable
private fun rememberYearLimitation(
    min: DatePickerState,
    max: DatePickerState
): List<Int> {
    return remember(min, max) {
        (min.year..max.year).toList()
    }
}

@Composable
private fun rememberMonthEnables(
    current: DatePickerState,
    min: DatePickerState,
    max: DatePickerState
): List<Boolean> {
    return remember(current, min, max) {
        DatePickerState.MONTHS.map { month ->
            month.coerceMonthEnable(current, min, max)
        }
    }
}

@Composable
private fun rememberDayEnables(
    current: DatePickerState,
    min: DatePickerState,
    max: DatePickerState,
    lastDayInMonth: Int
): List<Boolean> {
    return remember(current, min, max, lastDayInMonth) {
        DatePickerState.DAYS.map {
            it.coerceDayEnable(
                current = current,
                min = min,
                max = max,
                lastDayInMonth = lastDayInMonth
            )
        }
    }
}


/**
 * 計算當月的最後一天
 * @return (當月最後一天，修改後的日期，是否有強制修正)
 */
private fun adjustDay(
    month: Int,
    year: Int,
    lastDayInMonth: Int,
    day: Int
): Triple<Int?, Int?, Boolean> {
    val newLastDayInMonth = lastDayInMonth(month, year)
    var outputFirst : Int?= null
    var outputSecond : Int? = null
    var inValid = false

    if (lastDayInMonth != newLastDayInMonth) {
        outputFirst = newLastDayInMonth
        if (day > newLastDayInMonth) {
            inValid = true
            outputSecond = lastDayInMonth
        }
    }
    return Triple(outputFirst, outputSecond, inValid)
}

private fun Int.coerceMonthEnable(
    current: DatePickerState,
    min: DatePickerState,
    max: DatePickerState
): Boolean {
    return when (current.year) {
        min.year -> this >= min.month
        max.year -> this <= max.month
        else -> true
    }
}

private fun Int.coerceDayEnable(
    current: DatePickerState,
    min: DatePickerState,
    max: DatePickerState,
    lastDayInMonth: Int
) : Boolean {
    return if (current.year == min.year && current.month == min.month) {
        this >= min.day
    } else if (current.year == max.year && current.month == max.month) {
        this <= max.day
    } else {
        this <= lastDayInMonth
    }
}


private fun lastDayInMonth(month: Int, year: Int): Int {
    return if (month != 2) {
        31 - (month - 1) % 7 % 2
    } else {
        if (year and 3 == 0 && (year % 25 != 0 || year and 15 == 0)) {
            29
        } else {
            28
        }
    }
}