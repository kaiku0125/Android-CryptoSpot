package com.kaiku.composecomponent.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.textfield.data.PassWordConfig
import com.kaiku.composecomponent.component.textfield.data.TextFieldConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.sdp

/**
 * 登入頁面使用者輸入框
 *
 * @param leadingIcon 標題icon
 * @param title 標題
 * @param dividerColor 分隔線顏色
 * @param text 使用者輸入
 * @param hint 尚未輸入前的提示字元
 */

data class LoginTextFieldConfig(
    val leadingIcon: @Composable (() -> Unit)? = null,
    val titleWidth: Dp? = null,
    val title: PocketTextConfig = PocketTextConfig(
        value = "標題"
    ),
    val dividerColor : Color = Color.White,
    val text: TextFieldConfig = TextFieldConfig(),
    val hint: PocketTextConfig = PocketTextConfig(),
    val testTag: String = ""
)


/**
 *  使用者輸入元件
 *
 *  @param loginTextFieldConfig 輸入相關 viewState
 *  @param pwdConfig 密碼相關 viewState
 *  @param onTextChange export 最新輸入
 *  @param onFocusChange export 焦點變化
 */
@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    loginTextFieldConfig: LoginTextFieldConfig = LoginTextFieldConfig(),
    pwdConfig: PassWordConfig = PassWordConfig(),
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit
) {

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val visualTransformation by remember(
        isPasswordVisible,
        pwdConfig.pwdVisualTransformation
    ) {
        derivedStateOf {
            if (pwdConfig.needPasswordSecurity && isPasswordVisible.not()) {
                pwdConfig.pwdVisualTransformation
            } else {
                VisualTransformation.None
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.sdp()))
            .background(
                color = color_414141,
                shape = RoundedCornerShape(8.sdp())
            )
            .onFocusChanged {
                onFocusChange.invoke(it.isFocused)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.sdp()))

        loginTextFieldConfig.leadingIcon?.invoke()

        if (loginTextFieldConfig.title.value.isNotEmpty()) {
            Spacer(modifier = Modifier.width(3.sdp()))
            PocketText(
                modifier = loginTextFieldConfig.titleWidth?.let {
                    Modifier.width(it)
                } ?: Modifier,
                config = loginTextFieldConfig.title
            )
        }
        Spacer(modifier = Modifier.width(10.sdp()))

        VerticalDivider(
            modifier = Modifier.height(30.sdp()).padding(horizontal = 6.sdp()),
            thickness = 1.sdp(),
            color = loginTextFieldConfig.dividerColor,
        )

        Spacer(modifier = Modifier.width(10.sdp()))

        PocketTextFieldComponent(
            modifier = Modifier.weight(1f).testTag(loginTextFieldConfig.testTag),
            value = loginTextFieldConfig.text.value,
            valueAlignment = loginTextFieldConfig.text.alignment,
            valueStyle = loginTextFieldConfig.text.style,
            hintConfig = loginTextFieldConfig.hint,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = visualTransformation,
            onFocusChange = {

            },
            onTextChange = {
                onTextChange.invoke(it)
            }
        )

        if (pwdConfig.needPasswordSecurity) {
            Spacer(modifier = Modifier.size(5.sdp()))
            PocketIconButton(
                modifier = Modifier.padding(end = 20.sdp()),
                iconSize = 25.sdp(),
                drawableRes = if (isPasswordVisible) {
                    pwdConfig.visualDrawable
                } else {
                    pwdConfig.inVisualDrawable
                },
                clickableConfig = ClickableConfig(),
                onIconClick = {
                    isPasswordVisible = isPasswordVisible.not()
                }
            )
        }

    }
}