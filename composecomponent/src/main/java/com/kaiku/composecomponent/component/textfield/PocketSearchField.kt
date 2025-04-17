package com.kaiku.composecomponent.component.textfield

import androidx.annotation.ColorRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.hoverColor
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp

@Composable
fun PocketSearchField(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    isEditable: Boolean = true,
    textConfig: PocketTextConfig = PocketTextConfig(
        value = "",
        style = text15Sp(),
        alignment = Alignment.CenterStart
    ),
    hintConfig: PocketTextConfig = PocketTextConfig(
        value = "輸入股票代碼或名稱",
        style = text13Sp(),
        textColor = color_9e9e9f,
        alignment = Alignment.CenterStart
    ),
    shape: Shape = RoundedCornerShape(8.sdp()),
    @ColorRes background: Color = color_414141,
    border: BorderStroke? = null,
    @ColorRes iconTint: Color = background.hoverColor(),
    onFocusChange: ((Boolean) -> Unit)? = null,
    onTextChange: ((String) -> Unit)? = null,
    onFieldClick: (() -> Unit)? = null
) {
    val mModifier = remember(isEditable) {
        derivedStateOf {
            if (isEditable) {
                modifier
            } else {
                modifier.clickableEffectConfig { onFieldClick?.invoke() }
            }
        }
    }.value

    Surface(
        modifier = mModifier,
        shape = shape,
        color = background,
        border = border
    ) {
        Row(
            modifier = rowModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.sdp()))
            Icon(
                modifier = Modifier.size(24.sdp()),
                painter = painterResource(id = R.drawable.lib_pocket_ic_search),
                contentDescription = "search_icon",
                tint = iconTint
            )

            PocketSpacer(width = 8)

            PocketTextFieldComponent(
                modifier = Modifier.weight(1f),
                isEditable = isEditable,
                value = textConfig.value,
                valueAlignment = textConfig.textAlign,
                valueStyle = textConfig.style,
                hintConfig = hintConfig,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                containerColor = background,
                onFocusChange = {
                    onFocusChange?.invoke(it)
                },
                onTextChange = {
                    onTextChange?.invoke(it)
                }
            )

            Crossfade(
                targetState = textConfig.value.isEmpty(),
                label = "iconCrossFade",
            ) { isEmpty ->
                if (!isEmpty) {
                    PocketIconButton(
                        drawableRes = R.drawable.lib_pocket_edittext_delete,
                        iconSize = 24.sdp(),
                        onIconClick = {
                            onTextChange?.invoke("")
                        }
                    )
                }
            }
            PocketSpacer(width = 8)
        }
    }
}