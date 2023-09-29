package com.kaiku.cryptospot.customView.checkboxfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.text.SimpleTextWithHint
import com.kaiku.cryptospot.presentation.theme.color_717071

@Composable
fun CheckBoxFieldComponent(
    modifier: Modifier = Modifier,
    isAONFieldEnabled : Boolean = true,
    isChecked: Boolean,
    contentText : String = "",
    hintText : String = "",
    paddingStart : Dp = 12.dp,
    onCheckChanged : () -> Unit,
    onFieldClick : () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onFieldClick.invoke()
            }
            .padding(start = paddingStart, end = 20.dp, top = 12.dp, bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = if(isAONFieldEnabled) isChecked else false,
            onCheckedChange = {
                onCheckChanged.invoke()
            },
            modifier = Modifier.size(24.dp),
            enabled = isAONFieldEnabled,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.White,
                disabledCheckedColor = color_717071
            )
        )

        Spacer(modifier = Modifier.width(10.dp))

        SimpleTextWithHint(
            content = contentText,
            hint = hintText
        )
    }
}

@Preview
@Composable
private fun CheckBoxFieldComponentPreview(){
    CheckBoxFieldComponent(
        isChecked = true,
        contentText = "54879487",
        hintText = "000..",
        onCheckChanged = {

        },
        onFieldClick = {

        }
    )
}
