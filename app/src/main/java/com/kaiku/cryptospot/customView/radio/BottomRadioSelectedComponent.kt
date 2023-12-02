package com.kaiku.cryptospot.customView.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.extension.clickableEffectConfig
import com.kaiku.cryptospot.extension.data.ClickableConfig
import com.kaiku.cryptospot.presentation.theme.color_333333
import com.kaiku.cryptospot.presentation.theme.text_17sp_400weight

@Composable
fun TrailingRadioSelectedComponent(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle : TextStyle = MaterialTheme.typography.text_17sp_400weight,
    isChecked: Boolean,
    background : Color = color_333333,
    onFieldClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .clickableEffectConfig(
                config = ClickableConfig(
                    needRipple = false
                ),
                onClick = {
                    onFieldClick.invoke()
                }
            )
            .height(45.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SimpleText(
            config = SimpleTextConfig(
                value = title,
                style = titleStyle,
                alignment = Alignment.CenterStart
            )
        )

        PocketRadioIconComponent(
            isChecked = isChecked,
            isEnabled = true,
            iconSize = 24.dp
        )
    }
}

@Preview
@Composable
private fun TrailingRadioSelectedComponentPreview() {

    val isChecked = remember { mutableStateOf(true) }

    TrailingRadioSelectedComponent(
        modifier = Modifier,
        title = "喝了搖曳",
        isChecked = isChecked.value,
        onFieldClick = {
            isChecked.value = isChecked.value.not()
        }
    )

}