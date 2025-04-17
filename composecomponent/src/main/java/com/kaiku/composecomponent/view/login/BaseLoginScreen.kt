package com.kaiku.composecomponent.view.login


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.color_c8c9ca
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.checkboxfield.CheckBoxFieldComponent
import com.kaiku.composecomponent.component.checkboxfield.CheckBoxFieldConfig
import com.kaiku.composecomponent.component.click.SurfaceWithClickableEffect
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketAnnotatedText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithBottomLine
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.component.text.TextWithIcon
import com.kaiku.composecomponent.component.text.TextWithIconConfig
import com.kaiku.composecomponent.component.textfield.LoginTextField
import com.kaiku.composecomponent.component.textfield.LoginTextFieldConfig
import com.kaiku.composecomponent.component.textfield.data.PassWordConfig
import com.kaiku.composecomponent.component.textfield.data.TextFieldConfig
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.extension.ratioHeight
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.model.rememberIconClickableConfig
import com.kaiku.composecomponent.utils.LoginPasswordVisualTransformation
import com.kaiku.composecomponent.utils.OnComposeLifecycleEvent
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text12Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.login.data.LoginStringRes
import com.kaiku.composecomponent.view.login.data.LoginViewAction
import com.kaiku.composecomponent.view.login.data.LoginViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.intuit.sdp.R as sdpR
import com.intuit.ssp.R as sspR

private const val COUNT_DOWN_TIME = 2

@Composable
fun BaseLoginScreen(
    modifier: Modifier = Modifier,
    stringRes: LoginStringRes = LoginStringRes.DEFAULT,
    viewState: LoginViewState,
    action: (LoginViewAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val actionLambda = remember<(LoginViewAction) -> Unit> {
        {
            action.invoke(it)
        }
    }

    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = sdpR.dimen._15sdp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        val (logo, input, thirdParty) = createRefs()

        LogoImageField(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(input.top)
            },
            viewState = viewState,
            stringRes = stringRes
        )

        AccountInputField(
            modifier = Modifier.constrainAs(input) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(thirdParty.top)
            },
            viewState = viewState,
            action = actionLambda
        )

        ThirdPartyField(
            modifier = remember {
                Modifier.constrainAs(thirdParty) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            },
            viewState = viewState,
            action = remember {
                action
            }
        )
    }
}

@Composable
private fun LogoImageField(
    modifier: Modifier = Modifier,
    stringRes: LoginStringRes,
    viewState: LoginViewState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.sdp())
                .ratioHeight(100.dp, false)
        ) {
            Image(
                modifier = Modifier.aspectRatio(289 / 100f, true),
                painter = painterResource(id = R.drawable.pocket_logo),
                contentDescription = "",
                colorFilter = ColorFilter.tint(viewState.logoColor)
            )
        }
        HeightSpacer(8.dp, true)
        PocketText(
            config = PocketTextConfig(
                value = stringRes.loginTitleText,
                style = text17Sp(500),
                textColor = viewState.logoColor,
                lineHeight = dimensionResource(id = sspR.dimen._18ssp).value.sp,
                maxLines = 2
            )
        )
        HeightSpacer(8.dp)
    }
}


@Composable
private fun AccountInputField(
    modifier: Modifier = Modifier,
    viewState: LoginViewState,
    action: (LoginViewAction) -> Unit
) {
    // TODO: 先用這個方法有時間再改
    OnComposeLifecycleEvent(
        onEvent = { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (viewState.isRememberIdChecked.not()) {
                        action.invoke(LoginViewAction.UserInputIdentityAction(""))
                    }
                }

                else -> Unit
            }
        }
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginTextField(
            modifier = Modifier.height(45.sdp()),
            loginTextFieldConfig = LoginTextFieldConfig(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.sdp()),
                        painter = painterResource(id = R.drawable.pocket_ic_user),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                titleWidth = 55.sdp(),
                title = PocketTextConfig(
                    value = "身分證",
                    style = text17Sp(500),
                    maxLines = 1
                ),
                text = TextFieldConfig(
                    value = viewState.identityDisplay,
                    style = text15Sp(400)
                ),
                hint = PocketTextConfig(
                    value = "請輸入身分證字號",
                    style = text15Sp(400),
                    textColor = color_9e9e9f,
                    alignment = Alignment.CenterStart,
                    maxLines = 1
                ),
                testTag = "account"
            ),
            onTextChange = {
                action.invoke(
                    LoginViewAction.UserInputIdentityAction(it)
                )
            },
            onFocusChange = {
                action.invoke(
                    LoginViewAction.UpdateIdentityFocusAction(it)
                )
            }
        )

        HeightSpacer(12.dp, true)

        var isFocus by remember { mutableStateOf(false) }

        var onTextAppend by remember { mutableStateOf(false) }

        var textLength by remember { mutableIntStateOf(0) }

        var countDownValue by remember { mutableIntStateOf(COUNT_DOWN_TIME) }

        val passwordVisualTransformation by remember(
            onTextAppend,
            isFocus
        ) {
            derivedStateOf {
                if (isFocus && onTextAppend) {
                    LoginPasswordVisualTransformation()
                } else {
                    PasswordVisualTransformation()
                }
            }
        }

        LaunchedEffect(onTextAppend) {
            if (onTextAppend) {
                val job = launch {
                    onTextAppend = true
                    while (countDownValue > 0) {
                        countDownValue--
                        delay(1000)
                    }
                }
                job.invokeOnCompletion {
                    onTextAppend = false
                }
            }
        }

        LoginTextField(
            modifier = Modifier.height(45.sdp()),
            loginTextFieldConfig = LoginTextFieldConfig(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.sdp()),
                        painter = painterResource(id = R.drawable.pocket_ic_password),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                titleWidth = 55.sdp(),
                title = PocketTextConfig(
                    value = "密碼",
                    style = text17Sp(500),
                    letterSpacing = dimensionResource(id = sspR.dimen._8ssp).value.sp,
                    maxLines = 1
                ),
                text = TextFieldConfig(
                    value = viewState.password,
                    style = text15Sp(),
                ),
                hint = PocketTextConfig(
                    value = "請輸入密碼",
                    style = text15Sp(),
                    textColor = color_9e9e9f,
                    alignment = Alignment.CenterStart,
                    maxLines = 1
                ),
                testTag = "password"
            ),
            pwdConfig = PassWordConfig(
                needPasswordSecurity = true,
                pwdVisualTransformation = passwordVisualTransformation
            ),
            onTextChange = {
                action.invoke(
                    LoginViewAction.UserInputPasswordAction(it)
                )
                if (it.length > textLength) {
                    // 若增加字元
                    onTextAppend = true
                    countDownValue = COUNT_DOWN_TIME
                }
                textLength = it.length
            },
            onFocusChange = {
                isFocus = it
            }
        )

        HeightSpacer(10.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.sdp()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CheckBoxFieldComponent(
                modifier = Modifier.padding(start = 3.sdp()),
                cbConfig = CheckBoxFieldConfig(
                    isChecked = viewState.isRememberIdChecked,
                    size = 18.sdp(),
                    checkedColor = Color.White,
                    checkedBgColor = MaterialTheme.colorScheme.primary,
                    unCheckedBgColor = MaterialTheme.colorScheme.background,
                    borderColor = if (viewState.isRememberIdChecked) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.White
                    },
                    borderThickness = 2.sdp() * 3 / 4, // figma -> 1.5dp
                    paddingCheckBoxToText = 8.sdp(),
                    contentTextConfig = PocketTextConfig(
                        value = "記住ID",
                        style = text15Sp()
                    )
                ),
                clickableConfig = ClickableConfig(
                    needSound = false,
                    needRipple = false
                ),
                onCheckChanged = {
                    action.invoke(
                        LoginViewAction.UpdateRememberIdAction(
                            isChecked = viewState.isRememberIdChecked.not()
                        )
                    )
                },
                onFieldClick = {
                    action.invoke(
                        LoginViewAction.UpdateRememberIdAction(
                            isChecked = viewState.isRememberIdChecked.not()
                        )
                    )
                }
            )

            PocketTextWithBottomLine(
                modifier = Modifier.padding(end = 1.sdp()),
                config = PocketTextConfig(
                    value = "忘記密碼",
                    style = text15Sp(),
                    textColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    action.invoke(LoginViewAction.ForgetPasswordAction)
                }
            )

        }

        HeightSpacer(5.dp)

        BiometricField(
            modifier = Modifier.height(75.sdp()),
            isEnable = viewState.isBiometricEnable,
            onClick = {
                action.invoke(LoginViewAction.BiometricLoginClick)
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PocketPrimaryButton(
                modifier = Modifier.weight(1f),
                config = PocketTextConfig(
                    value = "立即開戶",
                    style = text17Sp(500)
                ),
                height = 44.sdp(),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    action.invoke(LoginViewAction.OpenAccountAction)
                }
            )

            Spacer(modifier = Modifier.width(20.sdp()))

            PocketPrimaryButton(
                modifier = Modifier
                    .weight(1f)
                    .testTag("login"),
                config = PocketTextConfig(
                    value = "登入",
                    style = text17Sp(500)
                ),
                isEnable = viewState.isLoginValid,
                height = 44.sdp(),
                border = BorderStroke(0.dp, Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    action.invoke(LoginViewAction.LoginAction)
                }
            )

        }

    }
}

@Composable
private fun ThirdPartyField(
    modifier: Modifier = Modifier,
    viewState: LoginViewState,
    action: (LoginViewAction) -> Unit
) {
    val actionLambda = remember<(LoginViewAction) -> Unit> {
        {
            action.invoke(it)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        HeightSpacer(27.dp)

        ThirdPartyTitle()

        HeightSpacer(8.dp)

        ThirdPartyIcon(action = actionLambda)

        HeightSpacer(35.dp)

        ThirdPartySecurityProtection(action = actionLambda)

        HeightSpacer(8.dp)

        ThirdPartyPocketService(action = actionLambda)

        HeightSpacer(8.dp)

        PocketVersion(
            version = viewState.buildVersion,
            action = actionLambda
        )

        HeightSpacer(8.dp)
    }
}


@Composable
fun ThirdPartyTitle(modifier: Modifier = Modifier) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = dimensionResource(id = sspR.dimen._10ssp).value.sp,
                fontWeight = FontWeight(500),
                color = Color.White
            )
        ) {
            append("訪客登入，")
        }

        withStyle(
            style = SpanStyle(
                fontSize = dimensionResource(id = sspR.dimen._10ssp).value.sp,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append("免開戶")
        }

        withStyle(
            style = SpanStyle(
                fontSize = dimensionResource(id = sspR.dimen._10ssp).value.sp,
                fontWeight = FontWeight(500),
                color = Color.White
            )
        ) {
            append("即可看盤、選股、分析")
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(23.sdp()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth(),
            color = color_9e9e9f,
            thickness = 1.sdp()
        )

        Spacer(modifier = Modifier.width(8.sdp()))

        PocketAnnotatedText(text = annotatedText)

        Spacer(modifier = Modifier.width(8.sdp()))

        HorizontalDivider(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth(),
            color = color_9e9e9f,
            thickness = 1.sdp()
        )

    }
}

@Composable
private fun ThirdPartyIcon(
    modifier: Modifier = Modifier,
    action: (LoginViewAction) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(57.sdp()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextWithIcon(
            topConfig = TextWithIconConfig(
                drawableRes = R.drawable.pocket_ic_cmoney,
                drawableSize = dimensionResource(id = sdpR.dimen._25sdp),
                paddingToText = dimensionResource(id = sdpR.dimen._3sdp),
                clickableConfig = rememberIconClickableConfig(
                    iconSize = dimensionResource(id = sdpR.dimen._25sdp),
                    rippleColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    action.invoke(LoginViewAction.CMoneyLoginAction)
                }
            ),
            content = {
                PocketText(
                    config = PocketTextConfig(
                        value = "CMoney",
                        style = text12Sp()
                    )
                )
            }
        )

        TextWithIcon(
            topConfig = TextWithIconConfig(
                drawableRes = R.drawable.pocket_ic_facebook,
                drawableSize = dimensionResource(id = sdpR.dimen._25sdp),
                paddingToText = dimensionResource(id = sdpR.dimen._3sdp),
                clickableConfig = rememberIconClickableConfig(
                    iconSize = dimensionResource(id = sdpR.dimen._25sdp),
                    rippleColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    action.invoke(LoginViewAction.FacebookLoginAction)
                }
            ),
            content = {
                PocketText(
                    config = PocketTextConfig(
                        value = "Facebook",
                        style = text12Sp()
                    )
                )
            }
        )

        TextWithIcon(
            topConfig = TextWithIconConfig(
                drawableRes = R.drawable.pocket_ic_google,
                drawableSize = dimensionResource(id = sdpR.dimen._25sdp),
                paddingToText = dimensionResource(id = sdpR.dimen._3sdp),
                clickableConfig = rememberIconClickableConfig(
                    iconSize = dimensionResource(id = sdpR.dimen._25sdp),
                    rippleColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    action.invoke(LoginViewAction.GoogleLoginAction)
                }
            ),
            content = {
                PocketText(
                    config = PocketTextConfig(
                        value = "Google",
                        style = text12Sp()
                    )
                )
            }
        )
    }
}

@Composable
fun ThirdPartySecurityProtection(
    modifier: Modifier = Modifier,
    action: (LoginViewAction) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(17.sdp()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PocketText(
            config = PocketTextConfig(
                value = "登入即同意口袋證券的",
                style = text12Sp()
            )
        )
        PocketTextWithBottomLine(
            config = PocketTextConfig(
                value = "隱私權保護聲明",
                style = text12Sp(),
                textColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                action.invoke(LoginViewAction.PrivacyStatementAction)
            }
        )
        PocketText(
            config = PocketTextConfig(
                value = "與",
                style = text12Sp()
            )
        )
        PocketTextWithBottomLine(
            config = PocketTextConfig(
                value = "個資保護",
                style = text12Sp(),
                textColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                action.invoke(LoginViewAction.PersonalProtection)
            }
        )
    }
}

@Composable
private fun ThirdPartyPocketService(
    modifier: Modifier = Modifier,
    action: (LoginViewAction) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(21.sdp()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = "開戶進度查詢",
                textColor = MaterialTheme.colorScheme.primary,
                style = text15Sp(),
            ),
            onClick = {
                action.invoke(LoginViewAction.AccountOpeningQueryAction)
            }
        )

        PocketSpacer(width = 5)

        PocketText(
            config = PocketTextConfig(
                value = "|",
                textColor = MaterialTheme.colorScheme.primary,
                style = text15Sp()
            )
        )

        PocketSpacer(width = 5)

        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = "聯繫客服",
                textColor = MaterialTheme.colorScheme.primary,
                style = text15Sp(),
            ),
            onClick = {
                action.invoke(LoginViewAction.ContactCustomerServiceAction)
            }
        )

        PocketSpacer(width = 5)

        PocketText(
            config = PocketTextConfig(
                value = "|",
                textColor = MaterialTheme.colorScheme.primary,
                style = text15Sp()
            )
        )

        PocketSpacer(width = 5)

        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = "公告",
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = {
                action.invoke(LoginViewAction.AnnouncementAction)
            }
        )

        PocketSpacer(width = 5)

        PocketText(
            config = PocketTextConfig(
                value = "|",
                textColor = MaterialTheme.colorScheme.primary,
                style = text15Sp()
            )
        )

        PocketSpacer(width = 5)

        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = "反詐騙專區",
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = {
                action.invoke(LoginViewAction.AntiFraudAction)
            }
        )
    }
}

@Composable
private fun PocketVersion(
    version: String,
    action: (LoginViewAction) -> Unit
) {
    val context = LocalContext.current
    val times = remember { mutableIntStateOf(0) }

    PocketTextWithClickEffect(
        modifier = Modifier
            .fillMaxWidth()
            .height(17.sdp()),
        config = PocketTextConfig(
            value = version,
            textColor = color_c8c9ca,
            style = text12Sp(),
        ),
        clickableConfig = ClickableConfig(
            needRipple = false,
            needSound = false,
            needHaptic = false
        ),
        onClick = {
            times.intValue++
            if (times.intValue >= 15) {
                action.invoke(LoginViewAction.BuildConfigClickAction)
                Toast.makeText(context, "?", Toast.LENGTH_SHORT).show()
                times.intValue = 0
            }
        }
    )
}


@Composable
private fun BiometricField(
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (isEnable) {
        MaterialTheme.colorScheme.primary
    } else {
        color_717071
    }

    SurfaceWithClickableEffect(
        modifier = modifier.pocketPadding(all = 5),
        shape = RoundedCornerShape(4.sdp()),
        color = Color.Transparent,
        isScaleEnabled = isEnable,
        clickableConfig = rememberClickableConfig(
            needRipple = isEnable,
            needSound = isEnable,
            needHaptic = isEnable
        ),
        content = {
            Box(
                modifier = Modifier.padding(10.sdp())
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.sdp()),
                        painter = painterResource(id = R.drawable.pocket_ic_faceid),
                        contentDescription = null,
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    PocketText(
                        config = PocketTextConfig(
                            value = "/",
                            style = text15Sp(500),
                            textColor = color
                        )
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    Icon(
                        modifier = Modifier.size(24.sdp()),
                        painter = painterResource(id = R.drawable.pocket_ic_fingerprint),
                        contentDescription = null,
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(4.sdp()))
                    PocketText(
                        config = PocketTextConfig(
                            value = "生物辨識登入",
                            style = text15Sp(500),
                            textColor = color
                        )
                    )
                }
            }
        },
        onClick = {
            if (isEnable) {
                onClick.invoke()
            }
        }
    )
}

@Composable
private fun HeightSpacer(
    height: Dp,
    isUnderSizeConstant: Boolean = false, // spacer的部分是否需要根據螢幕高度來做比例縮放
) {
    Spacer(
        modifier = Modifier.ratioHeight(
            figmaHeight = height,
            isUnderSizeConstant = isUnderSizeConstant
        )
    )
}