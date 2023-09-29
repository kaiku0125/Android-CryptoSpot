package com.kaiku.cryptospot.customView.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.icon.SimpleIconButton
import com.kaiku.cryptospot.presentation.theme.color_414141
import com.kaiku.cryptospot.presentation.theme.color_pocket_primary

/**
 * @sample PlusMinusTextFieldComponent 擁有+-按鈕的元件，並可以讓使用者輸入想要的數字
 * 
 * @param backgroundColor 元件背景顏色
 * @param value 輸入的內容
 * @param isPlusEnabled + 按鈕 是否enable
 * @param isMinusEnabled - 按鈕 是否enable
 * @param onTextChange 當輸入內容直接被改變
 * @param onPlusClick + 按鈕被點擊
 * @param onMinusClick - 按鈕被點擊
 */

@Composable
fun PlusMinusTextFieldComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = color_414141,
    value: Int,
    isPlusEnabled: Boolean = true,
    isMinusEnabled: Boolean = true,
    onTextChange: (String) -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val backUpVolume = remember(key1 = value) { mutableStateOf(value.toString()) }

    val displayVolume = remember(key1 = value) { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SimpleIconButton(
            modifier = Modifier
                .weight(10f),
            drawableRes = R.drawable.ic_gift,
            tint = color_pocket_primary,
            iconSize = 24.dp
        ) {
            onMinusClick.invoke()
            focusManager.clearFocus()
        }

        PocketTextFieldComponent(
            modifier = Modifier
                .weight(27f)
                .onFocusChanged {
                    if (it.isFocused.not()) {
                        if (displayVolume.value.isEmpty()) {
                            displayVolume.value = backUpVolume.value
                        }
                    }
                },
            value = displayVolume.value,
            hint = "",
            onTextChange = {
                if (it.isEmpty()) {
                    displayVolume.value = ""
                } else {
                    if(it == backUpVolume.value){
                        displayVolume.value = backUpVolume.value
                    }
                    onTextChange.invoke(it)
                }
            }
        )

        SimpleIconButton(
            modifier = Modifier
                .weight(10f),
            drawableRes = R.drawable.ic_congratulation,
            tint = color_pocket_primary,
            iconSize = 24.dp
        ) {
            onPlusClick.invoke()
            focusManager.clearFocus()
        }
    }
}

@Preview
@Composable
private fun PlusMinusTextFieldComponent(){
    PlusMinusTextFieldComponent(
        backgroundColor = Color.Black,
        value = 7777,
        onTextChange = {},
        onPlusClick = {},
        onMinusClick = {}
    )
}
