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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.presentation.theme.color_414141
import com.kaiku.cryptospot.presentation.theme.color_9e9e9f
import com.kaiku.cryptospot.presentation.theme.text_13sp_400weight
import com.kaiku.cryptospot.presentation.theme.text_15sp_400weight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketTextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    valueAlignment: TextAlign = TextAlign.Center,
    valueStyle: TextStyle = MaterialTheme.typography.text_15sp_400weight,
    hint: String,
    hintAlignment: Alignment =  Alignment.Center,
    hintStyle: TextStyle = MaterialTheme.typography.text_13sp_400weight,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Decimal,
        imeAction = ImeAction.Done
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val lambda = remember<(String) -> Unit>{
        {
            onTextChange.invoke(it)
        }
    }


    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = lambda,
        keyboardOptions = keyboardOptions,
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            textAlign = valueAlignment,
            fontSize = valueStyle.fontSize,
            fontWeight = valueStyle.fontWeight
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    ) {
        val containerColor = color_414141
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = visualTransformation,
            placeholder = {
                SimpleText(
                    modifier = Modifier.fillMaxWidth(),
                    config = SimpleTextConfig(
                        value = hint,
                        style = hintStyle,
                        textColor = color_9e9e9f,
                        alignment = hintAlignment,
                    )
                )
            },
            interactionSource = remember { MutableInteractionSource() },
            // keep horizontal paddings but change the vertical
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
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