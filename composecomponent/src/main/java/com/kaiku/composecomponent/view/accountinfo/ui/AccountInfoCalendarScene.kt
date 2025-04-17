package com.kaiku.composecomponent.view.accountinfo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickableEffect
import com.kaiku.composecomponent.extension.getColorAnimation
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text11Sp
import com.kaiku.composecomponent.utils.text15Sp

@Composable
fun AccountInfoCalendarScene(
    modifier: Modifier = Modifier,
    startDate: String? = null,
    endDate: String? = null,
    onRefresh: (() -> Unit)? = null,
    refreshDate: String = "YYYY/MM/DD 00:00:00",
    onPickStartDate: () -> Unit,
    onPickEndDate: () -> Unit,
    dateVisibility: ((Boolean) -> Unit)? = null,
    filterContent: @Composable RowScope.() -> Unit
) {

    var isDateVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            filterContent.invoke(this)

            PocketIconButton(
                iconModifier = Modifier.size(19.sdp()),
                drawableRes = R.drawable.lib_pocket_ic_calendar,
                iconSize = 24.sdp(),
                isEnable = isDateVisible.not(),
                tint = getColorAnimation(toColor = if (isDateVisible) color_717071 else Color.White),
                onIconClick = {
                    isDateVisible = true
                    dateVisibility?.invoke(isDateVisible)
                }
            )

            onRefresh?.let { refreshLambda ->
                Spacer(modifier = Modifier.width(8.sdp()))
                PocketIconButton(
                    drawableRes = R.drawable.lib_pocket_ic_refresh,
                    iconSize = 24.sdp(),
                    onIconClick = refreshLambda
                )
            }
        }

        PocketSpacer(height = 8)

        AnimatedVisibility(
            visible = isDateVisible,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PocketText(
                        config = PocketTextConfig(
                            value = "查詢區間",
                            style = text15Sp()
                        )
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    PocketTextWithClickableEffect(
                        modifier = Modifier
                            .height(30.sdp())
                            .weight(1f),
                        shape = RoundedCornerShape(8.sdp()),
                        color = color_414141,
                        config = PocketTextConfig(
                            value = startDate ?: "YYYY/MM/DD",
                            style = text15Sp(),
                            textColor = startDate?.let { Color.White } ?: color_9e9e9f
                        ),
                        onClick = onPickStartDate
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    PocketText(
                        config = PocketTextConfig(
                            value = "～",
                            style = text15Sp(),
                        )
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    PocketTextWithClickableEffect(
                        modifier = Modifier
                            .height(30.sdp())
                            .weight(1f),
                        shape = RoundedCornerShape(8.sdp()),
                        color = color_414141,
                        config = PocketTextConfig(
                            value = endDate ?: "YYYY/MM/DD",
                            style = text15Sp(),
                            textColor = endDate?.let { Color.White } ?: color_9e9e9f
                        ),
                        onClick = onPickEndDate
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    PocketIconButton(
                        drawableRes = R.drawable.lib_pocket_ic_date_delete,
                        iconSize = 24.sdp(),
                        onIconClick = {
                            isDateVisible = false
                            dateVisibility?.invoke(isDateVisible)
                        }
                    )

                }
                PocketSpacer(height = 8)
            }
        }

        onRefresh?.let {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment . CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    PocketText(
                        config = PocketTextConfig(
                            value = "更新時間：",
                            style = text11Sp(),
                            textColor = color_9e9e9f
                        )
                    )
                    AnimatedPocketText(
                        config = PocketTextConfig(
                            value = refreshDate,
                            style = text11Sp(),
                            textColor = color_9e9e9f
                        )
                    )
                }
                PocketSpacer(height = 12)
            }
        }

    }
}