package com.kaiku.cryptospot.presentation.flash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.customView.checkboxfield.CheckBoxFieldComponent
import com.kaiku.cryptospot.customView.dialog.RatioDialogComponent
import com.kaiku.cryptospot.customView.dialog.data.FlashRatioType
import com.kaiku.cryptospot.customView.spinner.SpinnerComponent
import com.kaiku.cryptospot.customView.spinner.data.FlashConditionJudgeType
import com.kaiku.cryptospot.customView.spinner.data.FlashConditionTriggerType
import com.kaiku.cryptospot.customView.tab.CryptoTabWithDividerFillMaxWidthComponent
import com.kaiku.cryptospot.customView.tab.data.CryptoTabType
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.textfield.PlusMinusTextFieldComponent
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.presentation.theme.text_15sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashUIScreen(
    channelID: String,
    score: Int,
    isTesting: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "flash order 測試")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            ScreenNavigator.back(TestDestination.route)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        ConstraintLayout(
            modifier = Modifier.padding(innerPadding)
        ) {

            val (test, flashOrderView, btn) = createRefs()

            Text(
                modifier = Modifier.constrainAs(test) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = "$channelID,__ $score,__ $isTesting",
                style = MaterialTheme.typography.text_15sp
            )

            FlashOrderView(
                modifier = Modifier.constrainAs(flashOrderView) {
                    top.linkTo(test.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            var showDialog by remember { mutableStateOf(false) }

            var nowRatio by remember { mutableStateOf(FlashRatioType.getAll()[0]) }

            Button(
                modifier = Modifier.constrainAs(btn) {
                    top.linkTo(flashOrderView.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                },
                onClick = {
                    showDialog = showDialog.not()
                }
            ) {
                Text(text = "切換")
            }

            if (showDialog) {
                RatioDialogComponent(
                    currentItem = nowRatio,
                    onClick = {
                        nowRatio = when (it) {
                            FlashRatioType.RatioNormalOrder -> {
                                FlashRatioType.RatioNormalOrder
                            }

                            FlashRatioType.RatioConditionOrder -> {
                                FlashRatioType.RatioConditionOrder
                            }

                            else -> FlashRatioType.RatioNormalOrder
                        }
                    }
                )
            }

        }

    }
}

@Composable
private fun FlashOrderView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.LightGray)
    ) {

        FlashOrderSettingItem(
            title = "觸價單設定"
        )

        FlashOrderSettingItem(
            title = "觸發條件"
        ) {
            SpinnerComponent(
                modifier = Modifier.weight(33f),
                list = FlashConditionTriggerType.getAll(),
                onTypeChange = {

                }
            )

            Spacer(modifier = Modifier.weight(4f))

            SpinnerComponent(
                modifier = Modifier.weight(33f),
                list = FlashConditionJudgeType.getAll(),
                onTypeChange = {

                }
            )
        }

        FlashOrderSettingItem(
            title = ""
        ) {
            PlusMinusTextFieldComponent(
                modifier = Modifier.weight(70f),
                value = 5487,
                onTextChange = {

                },
                onPlusClick = { },
                onMinusClick = { }
            )
        }

        FlashOrderSettingItem(
            title = "交易類別"
        ) {
            CryptoTabWithDividerFillMaxWidthComponent(
                modifier = Modifier.weight(70f),
                selectedItemIndex = 1,
                items = CryptoTabType.getAll(),
                tabHeight = 35.dp,
                onClick = {

                }
            )
        }

        CheckBoxFieldComponent(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue),
            isChecked = true,
            contentText = "54879487",
            hintText = "7777...",
            onCheckChanged = { /*TODO*/ },
            onFieldClick = {}
        )

    }
}

@Composable
private fun FlashOrderSettingItem(
    title: String = "",
    content: @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SimpleText(
            modifier = Modifier.weight(30f),
            alignment = Alignment.CenterStart,
            text = title
        )

        content?.invoke(this)
    }
}


object FlashUIScreenTag {
    const val CHANNEL_ID = "channel_id"
    const val SCORE = "score"
    const val IS_TESTING = "is_testing"
}

