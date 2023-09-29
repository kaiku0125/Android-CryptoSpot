package com.kaiku.cryptospot.customView.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.presentation.theme.color_333333
import com.kaiku.cryptospot.presentation.theme.color_9e9e9f
import com.kaiku.cryptospot.presentation.theme.text_13sp_400weight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketTextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onTextChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { inputValue ->
            onTextChange(inputValue)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    ) {
        val containerColor = color_333333


        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                SimpleText(
                    modifier = Modifier.fillMaxWidth(),
                    text = hint,
                    style = MaterialTheme.typography.text_13sp_400weight,
                    textColor = color_9e9e9f
                )
            },
            interactionSource = remember { MutableInteractionSource() },
            // keep horizontal paddings but change the vertical
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp
            ),
//            colors = TextFieldDefaults.textFieldColors(
//                containerColor = color_333333,
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
//                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
//                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
//                disabledIndicatorColor = MaterialTheme.colorScheme.primary
//            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = containerColor,
                unfocusedIndicatorColor = containerColor,
                disabledIndicatorColor = containerColor,
            )

        )
    }
}