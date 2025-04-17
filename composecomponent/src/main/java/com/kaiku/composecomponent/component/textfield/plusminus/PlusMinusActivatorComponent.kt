package com.kaiku.composecomponent.component.textfield.plusminus

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.button.PocketLongPressIcon
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.textfield.plusminus.data.PlusMinusActivatorState
import com.kaiku.composecomponent.component.textfield.plusminus.data.PlusMinusErrorEvent
import com.kaiku.composecomponent.component.textfield.plusminus.data.PlusMinusLogicStrategy
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.getColorAnimation
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.utils.localDrawableProvider
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

/**
 * PlusMinusActivatorComponent 帶有『加減按鈕』的使用者輸入元件
 * 對元件輸入input，並使用『策略模式』來，來控制提供[logic]的時機
 *
 * 例如：若今天元件的輸入情況會根據標的而不同，應在切換標的的同時，emit一個新的[logic]給元件
 *
 * !! 注意 !!
 * 若 [input] flow emit過快，會導致UI無法明確響應，為了防止漏值導致非同步的意外:
 * ✅ 減少外部emit數量
 * ✅ 將大部分在要在極短時間內強制轉換的過程，都放進[logic]的input2output實作中
 *
 * 目前元件的流程是 ➔
 * 1. 點擊[onFieldClick]叫外部的鍵盤
 * 2. 鍵盤輸入後，走[input]進來
 * 3. 根據[logic]來改變UI狀態
 * 4. 最後將顯示結果[onSync]出去 (以達成UI與外部vm資料同步目的)
 *   『加減邏輯』則由[logic]實作運算，完成後再向外同步
 *
 * @param drawableProvider drawableRes提供者
 * @param logic 元件+-邏輯策略 (需實作 PlusMinusLogicStrategy)
 * @param input 外部輸入 (例如鍵盤輸入)
 * @param valueColor 文字顯示顏色
 * @param hint 提示文字
 * @param unit 文案unit
 * @param tint drawable顏色
 * @param tag debug用的tag (預設為空)
 * @param needUpdateInputWhenNewLogic 當有新的策略時，需不需要重新更新輸入值
 * @param compareEqual 定義內外同步比較邏輯，預設 a != b
 * @param onSync export 與父類同步事件
 * @param onError export 錯誤事件
 * @param onFieldClick export 點擊區域事件
 */
@Composable
fun <T> PlusMinusActivatorComponent(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    logic: PlusMinusLogicStrategy<T>,
    input: T? = null,
    valueColor: Color = getColorAnimation(toColor = MaterialTheme.colorScheme.onSurface),
    hint: String = "輸入成交價",
    unit: String = "元",
    tint: Color = Color.Unspecified,
    tag: String = "",
    needUpdateInputWhenNewLogic: Boolean = true,
    compareEqual: (T?, T?) -> Boolean = { a, b -> a != b },
    onSync: (T?) -> Unit,
    onError: ((PlusMinusErrorEvent) -> Unit)? = null,
    onFieldClick: () -> Unit = {}
) {
    var vs by remember { mutableStateOf(PlusMinusActivatorState<T>()) }

    // 是否檢查過與外部同步 (view啟動時會檢查一次)
    var isFirstCheck by remember { mutableStateOf(false) }

    // 當有新的值輸入時，產生元件的vs，並與外部同步
    val onNewInput: (T?) -> Unit = { newInput ->
        vs = logic.toViewState(newInput).also { state ->
            state.errorEvent?.let { event -> onError?.invoke(event) }
        }
        onSync.invoke(vs.input)
        debug(tag = tag, msg = "onSync parent: ${vs.input} with [${logic.hashCode()}]")
    }

    // 當+-按鈕觸發，先進行邏輯運算，
    // 再將運算的結果以 onNewInput 的方式模擬外部輸入
    val onPlusMinus: (isPlus: Boolean) -> Unit = { isPlus ->
        val plusMinusValue = logic.calculatePlusMinusResult(
            isPlus = isPlus,
            value = vs.input
        )
        onNewInput(plusMinusValue)
    }

    // view啟動時，先與外部的值同步
    LaunchedEffect(Unit) {
        onNewInput(input)
        isFirstCheck = true
    }

    // 監聽有新的邏輯策略時，把當前的值當作新的輸入重新跑過邏輯
    LaunchedEffect(logic, isFirstCheck, needUpdateInputWhenNewLogic) {
        debug(tag = tag, "newLogic: ${logic.hashCode()}")
        if (isFirstCheck && needUpdateInputWhenNewLogic) {
            onNewInput(input)
        }
    }

    // 監聽外部輸入
    LaunchedEffect(input, isFirstCheck) {
        snapshotFlow { input to isFirstCheck }
            .collectLatest { (newInput, isChecked) ->
                if (compareEqual(newInput, vs.input) && isChecked) {
                    debug(tag = tag, msg = "compareInput : new ➔ $newInput, old ➔ ${vs.input}")
                    onNewInput(newInput)
                }
            }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PocketSpacer(width = 12)
        PocketLongPressIcon(
            drawableRes = drawableProvider.minus,
            tint = tint,
            size = 24.sdp(),
            isEnabled = vs.isMinusEnable,
            onClick = {
                if (vs.isMinusEnable) {
                    onPlusMinus(false)
                }
            }
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .pocketPadding(hv = 10 to null)
                .clickableEffectConfig { onFieldClick.invoke() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedPocketText(
                config = PocketTextConfig(
                    value = vs.display.ifEmpty { hint },
                    style = if (vs.display.isEmpty()) text15Sp() else text17Sp(),
                    textColor = if (vs.display.isEmpty()) {
                        color_9e9e9f
                    } else {
                        valueColor
                    }
                ),
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith
                            fadeOut(animationSpec = tween(durationMillis = 300))
                },
                colorKey = vs.display to valueColor
            )
            Spacer(modifier = Modifier.width(8.sdp()))
            PocketText(
                config = PocketTextConfig(
                    value = unit,
                    style = text13Sp(),
                    textColor = getColorAnimation(toColor = MaterialTheme.colorScheme.onSurface)
                )
            )
        }

        PocketLongPressIcon(
            drawableRes = drawableProvider.plus,
            tint = tint,
            size = 24.sdp(),
            isEnabled = vs.isPlusEnable,
            onClick = {
                if (vs.isPlusEnable) {
                    onPlusMinus(true)
                }
            }
        )
        PocketSpacer(width = 12)
    }
}

private fun debug(tag: String, msg: String) {
    if (tag.isNotEmpty()) {
        Timber.tag(tag).d(msg)
    }
}
