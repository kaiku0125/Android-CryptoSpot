package com.kaiku.composecomponent.component.textfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @param value 輸入的內容(String)
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PocketTextFieldComponent(
    modifier: Modifier = Modifier,
    isEditable: Boolean = true,
    value: String,
    valueAlignment: TextAlign = TextAlign.Center,
    valueStyle: TextStyle = TextStyle.Default,
    hintConfig: PocketTextConfig = PocketTextConfig(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Decimal,
        imeAction = ImeAction.Done
    ),
    containerColor: Color = color_414141,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChange: (Boolean) -> Unit,
    onTextChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    val lambda = remember<(String) -> Unit> {
        {
            onTextChange.invoke(it)
        }
    }

    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }

    val isFocused by interactionSource.collectIsFocusedAsState()
    LaunchedEffect(isFocused) {
        onFocusChange.invoke(isFocused)
        if (isFocused) {
            delay(400)
            bringIntoViewRequester.bringIntoView()
        }
    }

    BasicTextField(
        modifier = modifier,
        enabled = isEditable,
        value = value,
        onValueChange = {
            lambda.invoke(it)
            scope.launch {
                bringIntoViewRequester.bringIntoView()
            }
        },
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
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    ) {
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            singleLine = true,
            enabled = isEditable,
            visualTransformation = visualTransformation,
            placeholder = {
                PocketText(
                    modifier = Modifier.fillMaxWidth(),
                    config = hintConfig
                )
            },
            interactionSource = interactionSource,
            // keep horizontal paddings but change the vertical
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = containerColor,
                unfocusedIndicatorColor = containerColor,
                disabledIndicatorColor = containerColor
            )
        )
    }
}

/**
 * @param textFieldValue 輸入的內容(TextFieldValue)
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PocketTextFieldComponent(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    valueAlignment: TextAlign = TextAlign.Center,
    valueStyle: TextStyle = TextStyle.Default,
    hintConfig: PocketTextConfig = PocketTextConfig(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Decimal,
        imeAction = ImeAction.Done
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChange: (Boolean) -> Unit,
    onTextChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    val lambda = remember<(String) -> Unit> {
        {
            onTextChange.invoke(it)
        }
    }

    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }

    val isFocused by interactionSource.collectIsFocusedAsState()
    LaunchedEffect(isFocused) {
        onFocusChange.invoke(isFocused)
        if (isFocused) {
            delay(400)
            bringIntoViewRequester.bringIntoView()
        }
    }

    BasicTextField(
        modifier = modifier,
        value = textFieldValue,
        onValueChange = {
            lambda.invoke(it.text)
            scope.launch {
                bringIntoViewRequester.bringIntoView()
            }
        },
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
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    ) {
        val containerColor = color_414141
        TextFieldDefaults.DecorationBox(
            value = textFieldValue.text,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            visualTransformation = visualTransformation,
            placeholder = {
                PocketText(
                    modifier = Modifier.fillMaxWidth(),
                    config = hintConfig
                )
            },
            interactionSource = interactionSource,
            // keep horizontal paddings but change the vertical
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                focusedIndicatorColor = containerColor,
                unfocusedIndicatorColor = containerColor,
                disabledIndicatorColor = containerColor
            )
        )
    }
}
