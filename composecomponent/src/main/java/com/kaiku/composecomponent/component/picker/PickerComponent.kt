package com.kaiku.composecomponent.component.picker

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.kaiku.composecomponent.color_717071

@Composable
fun PickerComponent(
    modifier: Modifier = Modifier,
    label: (Int) -> String = {
        it.toString()
    },
    value: Int,
    dividersColor: Color = color_717071,
    range: Iterable<Int>,
    textStyle: TextStyle = LocalTextStyle.current,
    onValueChange: (Int) -> Unit,
) {
    ListItemPicker(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        dividersColor = dividersColor,
        list = range.toList().sortedDescending(),
        textStyle = textStyle
    )
}