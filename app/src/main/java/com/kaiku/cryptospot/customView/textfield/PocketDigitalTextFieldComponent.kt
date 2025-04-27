package com.kaiku.cryptospot.customView.textfield

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.cryptospot.customView.keyboard.DigitalKeyboardComponent
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme
import com.kaiku.cryptospot.presentation.theme.color_333333
import com.kaiku.cryptospot.presentation.theme.color_9e9e9f
import kotlinx.coroutines.launch

/**
 * @sample PocketDigitalTextFieldComponent compose數字鍵盤
 *
 * @param value 顯示使用者輸入預設值
 * @param onTextChange export使用者輸入的值
 * @param hint 當輸入為空時之提示字元
 * @param leadingSpace 前導UI
 * @param trailingSpace 尾部UI
 * @param backgroundColor 背景顏色
 * @param isNumberError 是否符合邏輯
 * @param isKeyboardActive 點擊是否要啟動鍵盤
 * @param isKeyboardDotEnable 鍵盤是否可以點擊 "."
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketDigitalTextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    onTextChange: (String) -> Unit,
    hint: String = "",
    leadingSpace: @Composable (RowScope.() -> Unit)? = null,
    trailingSpace: @Composable (RowScope.() -> Unit)? = null,
    @ColorRes backgroundColor: Color = com.kaiku.composecomponent.color_333333,
    isNumberError: Boolean = false,
    isKeyboardActive: Boolean = true,
    isKeyboardDotEnable: Boolean = true,
) {
    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(8.sdp()),
        border = BorderStroke(width = 1.sdp(), color = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .height(35.sdp())
                .clickable {
                    keyboardController?.hide()
                    if (isKeyboardActive) {
                        isSheetOpen = true
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingSpace?.invoke(this)
            PocketTextFieldItem(
                value = value,
                hint = hint,
                onTextChange = onTextChange
            )
            trailingSpace?.invoke(this)
        }

        if (isSheetOpen) {
            DigitalKeyboardComponent(
                initValue = value,
                hint = hint,
                isKeyboardDotEnable = isKeyboardDotEnable,
                sheetState = sheetState,
                onConfirm = {
                    scope.launch {
                        sheetState.hide()
                        isSheetOpen = false
                    }
                    if (it.isConformToKeyboardOutput()) {
                        onTextChange.invoke(it)
                    }
                },
                onDismissRequest = {
                    isSheetOpen = false
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.PocketTextFieldItem(
    value: String,
    hint: String,
    enabled: Boolean = false,
    onTextChange: (String) -> Unit
) {
//    val marketPriceString = stringResource(id = R.string.market_price)

    val focusManager = LocalFocusManager.current
    BasicTextField(
        value = value,
        onValueChange = { inputValue ->
            // 確保使用者輸入為BigDecimal
            if (inputValue.isEmpty() || inputValue.toBigDecimalOrNull() != null) {
                onTextChange(inputValue)
            }
        },
        modifier = Modifier.weight(1f),
//            .onFocusChanged {
//                if (it.isFocused && value == marketPriceString) {
//                    viewModel.setComposeViewRequest(ComposeViewRequest.UserClickMarketPriceToLatestPrice)
//                }
//            },
        enabled = enabled,
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
            enabled = enabled,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    text = hint,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = text13Sp(),
                    color = color_9e9e9f
                )
            },
            interactionSource = remember { MutableInteractionSource() },
            // keep horizontal paddings but change the vertical
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp, bottom = 0.dp
            ),
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

/**
 * 檢查 compose 鍵盤輸出是否符合邏輯邏輯
 */
private fun String.isConformToKeyboardOutput(): Boolean {
    var isAvailable = false

    if (this.isEmpty()) {
        isAvailable = true
    } else {
        if (this.isNumeric() && this.toDoubleOrNull() != 0.0) {
            isAvailable = true
        }
    }

    return isAvailable
}

private fun String.isNumeric(): Boolean {
    return this.matches("-?\\d+(\\.\\d+)?".toRegex())
}

@Composable
@Preview
private fun PreviewInputVolume() {
    CryptoSpotTheme {
        val inputText = remember { mutableStateOf("") }
        PocketDigitalTextFieldComponent(
            value = inputText.value,
            onTextChange = { inputText.value = it },
            hint = "請輸入委託價格",
            leadingSpace = {
                PocketSpacer(width = 8)
                Text(text = "小於等於", color = Color.White)
            },
            trailingSpace = {
                Text(text = "股", color = Color.White)
                PocketSpacer(width = 8)
            }
        )
    }
}
