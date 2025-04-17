package com.kaiku.composecomponent.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp


/**
 * @param primaryColor 主要顏色
 * @param dialogBackgroundColor 對話框背景顏色
 * @param positiveTextColor 確定按鈕文字顏色
 * @param title 標題
 * @param contentMessage 內容
 * @param contentPaddingHorizontal 內容水平間距(直接填入figma大小)
 * @param contentPaddingVertical 內容垂直間距(直接填入figma大小)
 * @param buttonPaddingBetween 兩按鈕間距
 * @param buttonShape 按鈕形狀
 * @param positiveText 確定按鈕文字
 * @param negativeText 取消按鈕文字
 * @param onPositiveClick export 確定按鈕點擊事件
 * @param onNegativeClick export 取消按鈕點擊事件
 * @param positiveButton 確定按鈕UI
 * @param negativeButton 取消按鈕UI
 * @param trailing dialog下方UI
 * @param content 內容UI
 * @param bottomContent 確定與取消按鈕下方UI
 */
@Composable
fun PocketComposeDialog(
    modifier: Modifier = Modifier,
    btnModifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    dialogBackgroundColor: Color = color_252525,
    positiveTextColor: Color? = null,
    title: String? = null,
    contentMessage: String = "",
    contentPaddingHorizontal: Int = 40,
    contentPaddingVertical: Int = 8,
    buttonPaddingBetween: Dp = 8.sdp(),
    buttonShape: Shape = RoundedCornerShape(8.sdp()),
    positiveText: String? = "確定",
    negativeText: String? = "取消",
    onPositiveClick: (() -> Unit)? = null,
    onNegativeClick: (() -> Unit)? = null,
    positiveButton: (@Composable RowScope.() -> Unit)? = {
        PocketPrimaryButton(
            modifier = Modifier.weight(1f),
            config = PocketTextConfig(
                value = positiveText ?: "確定",
                style = text17Sp(700),
                textColor = positiveTextColor ?: Color.White,
                maxLines = 1
            ),
            shape = buttonShape,
            primaryColor = primaryColor,
            onClick = {
                onPositiveClick?.invoke()
            }
        )
    },
    negativeButton: (@Composable RowScope.() -> Unit)? = {
        PocketPrimaryButton(
            modifier = Modifier.weight(1f),
            config = PocketTextConfig(
                value = negativeText ?: "取消",
                style = text17Sp(700),
                textColor = primaryColor,
                maxLines = 1
            ),
            shape = buttonShape,
            border = BorderStroke(1.sdp(), primaryColor),
            primaryColor = Color.Transparent,
            onClick = {
                onNegativeClick?.invoke()
            }
        )
    },
    trailing: (@Composable ColumnScope.() -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit) = {
        PocketText(
            modifier = Modifier
                .defaultMinSize(minHeight = 60.sdp())
                .fillMaxWidth(),
            config = PocketTextConfig(
                value = contentMessage,
                style = text15Sp(),
                textAlign = TextAlign.Center,
                maxLines = Int.MAX_VALUE
            )
        )
    },
    bottomContent: (@Composable ColumnScope.() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        ),
    ) {
        Card(
            modifier = modifier
                .pocketPadding(hv = 16 to null)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.sdp())
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .clipByShape(
                            shape = RoundedCornerShape(8.sdp()),
                            backgroundColor = dialogBackgroundColor
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!title.isNullOrBlank()) {
                        PocketText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.sdp())
                                .background(primaryColor),
                            config = PocketTextConfig(
                                value = title,
                                style = text17Sp(700),
                                textColor = positiveTextColor ?: Color.White,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        )
                    }

                    Column(
                        modifier = Modifier.pocketPadding(
                            hv = contentPaddingHorizontal to contentPaddingVertical
                        ),
                        verticalArrangement = Arrangement.Center
                    ) {
                        content.invoke(this)
                        if (!negativeText.isNullOrBlank() || !positiveText.isNullOrBlank()) {
                            Row(
                                modifier = btnModifier
                                    .fillMaxWidth()
                                    .pocketPadding(bottom = 20),
                                horizontalArrangement = Arrangement.spacedBy(buttonPaddingBetween)
                            ) {
                                negativeText?.let { negativeButton?.invoke(this) }
                                positiveText?.let { positiveButton?.invoke(this) }
                            }
                        }
                        bottomContent?.invoke(this)
                    }
                }
                trailing?.invoke(this)
            }
        }
    }
}