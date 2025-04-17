package com.kaiku.composecomponent.component.checkboxfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_e95465
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithHint
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig

/**
 *  @property isChecked 是否勾選
 *  @property size check box大小
 *  @property isEnable 是否啟用
 *  @property shape check box形狀
 *  @property borderThickness check box邊框粗細
 *  @property borderColor check box邊框顏色
 *  @property duration 勾勾動畫時間
 *  @property checkedColor 勾勾顏色 
 *  @property checkedBgColor 勾選時背景顏色
 *  @property unCheckedBgColor 未勾選時的背景顏色
 *  @property paddingCheckBoxToText CheckBox與文字間距
 *  @property contentTextConfig 內容文字設定
 *  @property hintTextConfig 提示文字設定
 */
data class CheckBoxFieldConfig(
    val isChecked: Boolean = false,
    val size: Dp = 24.dp,
    val isEnable: Boolean = true,
    val shape: Shape = RoundedCornerShape(4.dp),
    val borderThickness: Dp = 0.dp,
    val borderColor: Color = Color.White,
    val duration: Int = 500,
    val checkedColor: Color = Color.White,
    val checkedBgColor: Color = color_e95465,
    val unCheckedBgColor: Color = color_333333,
    val paddingCheckBoxToText: Dp = 10.dp,
    val contentTextConfig: PocketTextConfig = PocketTextConfig(),
    val hintTextConfig: PocketTextConfig = PocketTextConfig()
)


/**
 *  @param cbConfig CheckBox設定檔
 *  @param clickableConfig 點擊設定檔
 *  @param onCheckChanged export 勾選事件
 *  @param onFieldClick export 整條點擊事件
 */
@Composable
fun CheckBoxFieldComponent(
    modifier: Modifier = Modifier,
    cbConfig: CheckBoxFieldConfig = CheckBoxFieldConfig(),
    clickableConfig: ClickableConfig = ClickableConfig(),
    onCheckChanged: () -> Unit,
    onFieldClick: () -> Unit
) {

    Row(
        modifier = modifier
            .clickableEffectConfig(
                config = clickableConfig,
                onClick = onFieldClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        PocketCheckBoxComponent(
            isChecked = if (cbConfig.isEnable) cbConfig.isChecked else false,
            size = cbConfig.size,
            isEnable = cbConfig.isEnable,
            shape = cbConfig.shape,
            borderThickness = cbConfig.borderThickness,
            borderColor = cbConfig.borderColor,
            duration = cbConfig.duration,
            checkColor = cbConfig.checkedColor,
            checkedBgColor = cbConfig.checkedBgColor,
            unCheckedBgColor = cbConfig.unCheckedBgColor,
            onValueChange = {
                onCheckChanged.invoke()
            }
        )

        Spacer(modifier = Modifier.width(cbConfig.paddingCheckBoxToText))

        if (cbConfig.hintTextConfig.value.isNotEmpty()) {
            PocketTextWithHint(
                contentConfig = cbConfig.contentTextConfig,
                hintConfig = cbConfig.hintTextConfig
            )
        } else {
            PocketText(
                config = cbConfig.contentTextConfig
            )
        }

    }
}

