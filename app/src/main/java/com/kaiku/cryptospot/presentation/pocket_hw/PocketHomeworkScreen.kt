package com.kaiku.cryptospot.presentation.pocket_hw

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.spinner.SpinnerComponent
import com.kaiku.cryptospot.customView.spinner.data.CryptoSpinnerType
import com.kaiku.cryptospot.customView.tab.CryptoTabFillMaxWidthComponent
import com.kaiku.cryptospot.customView.tab.CryptoTabWithBadgeComponent
import com.kaiku.cryptospot.customView.tab.data.BadgeTabType
import com.kaiku.cryptospot.customView.tab.data.CryptoTabType
import com.kaiku.cryptospot.customView.text.AutoResizeText
import com.kaiku.cryptospot.customView.text.FontSizeRange
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.topappbar.ScaffoldTopAppBarWithBackNavComponent
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.navigation.TestDestination
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideEnter
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideExit
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun PocketHomeworkScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ScaffoldTopAppBarWithBackNavComponent(
                titleText = "口袋作業",
                needInformation = true,
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = "lock")
                    }
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "setting")
                    }
                },
                onNavigationClick = {
                    ScreenNavigator.back(TestDestination.route)
                }
            )
        }
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        var index by remember { mutableStateOf(0) }

        var backColor by remember { mutableStateOf(Color.White) }

        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
                .fillMaxSize()
                .background(backColor)
        ) {

            val (tabSmallRegion, tabSwitchRegion, firstRegion, secondRegion) = createRefs()

            val (cardTest, cardTest2) = createRefs()

            val (plus, undo, autoText) = createRefs()

            var currentItem by remember { mutableStateOf(BadgeTabType.getAll()[0]) }

            CryptoTabWithBadgeComponent(
                modifier = Modifier.constrainAs(tabSmallRegion) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
                selectedItemIndex = currentItem.position,
                items = BadgeTabType.getAll(),
                onClick = {
                    currentItem = when (it.position) {
                        BadgeTabType.TabOne.position -> BadgeTabType.TabOne
                        BadgeTabType.TabTwo.position -> BadgeTabType.TabTwo
                        BadgeTabType.TabThree.position -> BadgeTabType.TabThree
                        BadgeTabType.TabFour.position -> BadgeTabType.TabFour
                        else -> BadgeTabType.TabOne
                    }
                }
            )


            val text = remember { mutableStateOf("喝了搖曳") }

            val update = remember {
                mutableStateOf(false)
            }

            Button(
                modifier = Modifier.constrainAs(plus) {
                    top.linkTo(tabSmallRegion.bottom)
                },
                onClick = {
                    text.value = buildString {
                        append(text.value)
                        append("喝了搖曳")
                    }
                }
            ) {
                Text(text = "plus")
            }

            Button(
                modifier = Modifier.constrainAs(undo) {
                    top.linkTo(tabSmallRegion.bottom)
                    start.linkTo(plus.end)
                },
                onClick = {
                    text.value = "喝了搖曳"
                    update.value = true

                    backColor = if (backColor == Color.Black) {
                        Color.White
                    } else {
                        Color.Black
                    }
                }
            ) {
                Text(text = "undo")
            }

            AutoResizeText(
                modifier = Modifier
                    .width(200.dp)
                    .heightIn(max = 50.dp)
                    .constrainAs(autoText) {
                        top.linkTo(plus.bottom)
                    }
                    .background(Color.Magenta),
                text = text.value,
                fontSizeRange = FontSizeRange(10.sp, 28.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                readyToDraw = false,
                update = update.value,
                hasDrawn = {
                    update.value = false
                }
            )

            TestCard(
                modifier = Modifier
                    .constrainAs(cardTest) {
                        start.linkTo(parent.start, margin = 10.dp)
                        top.linkTo(autoText.bottom, margin = 10.dp)
                    }
            )

            Card(
                modifier = Modifier
                    .constrainAs(cardTest2) {
                        start.linkTo(cardTest.end, margin = 10.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                        top.linkTo(autoText.bottom, margin = 10.dp)
                    }
//                    .shadow(
//                        spotColor = Color.Green,
//                        elevation = 18.dp
//                    )
            ) {
                SimpleText(
                    modifier = Modifier.size(60.dp),
                    text = "7777",
                    textColor = Color.Black
                )
            }

            TabView(
                modifier = Modifier.constrainAs(tabSwitchRegion) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(cardTest.bottom, margin = 10.dp)
                }
            ) {
                scope.launch {
                    index = it
                }
            }

            AnimatedVisibility(
                visible = index == 0,
                enter = screenSlideEnter(fromLeft = true),
                exit = screenSlideExit(toLeft = true),
                modifier = Modifier.constrainAs(firstRegion) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tabSwitchRegion.bottom)
                }

            ) {
                FirstTabSpinnerView()
            }

            AnimatedVisibility(
                visible = index == 1,
                enter = screenSlideEnter(fromLeft = false),
                exit = screenSlideExit(toLeft = false),
                modifier = Modifier.constrainAs(secondRegion) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tabSwitchRegion.bottom)
                }
            ) {
                SecondTabSpinnerView()
            }

        }
    }
}

@Composable
fun TabView(
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit
) {
    val currentItem = remember { mutableStateOf(CryptoTabType.getAll()[0]) }

    CryptoTabFillMaxWidthComponent(
        modifier = modifier.fillMaxWidth(),
        selectedItemIndex = currentItem.value.position,
        items = CryptoTabType.getAll(),
        onClick = {
            currentItem.value = when (it.position) {
                CryptoTabType.TabBTC.position -> CryptoTabType.TabBTC
                CryptoTabType.TabETH.position -> CryptoTabType.TabETH
                CryptoTabType.TabADA.position -> CryptoTabType.TabADA
                else -> CryptoTabType.TabBTC
            }
            onTabClick.invoke(currentItem.value.position)
        }
    )
}

@Composable
fun ChoseCryptoSpinnerView() {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
    ) {

        val startGuide = createGuidelineFromStart(0.25f)

        val (text, spinner) = createRefs()

        val currentCryptoItem = remember { mutableStateOf(CryptoSpinnerType.getAll()[0]) }

        Text(
            text = "加密貨幣",
            modifier = Modifier.constrainAs(text) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        )

        SpinnerComponent(
            modifier = Modifier
                .constrainAs(spinner) {
                    start.linkTo(startGuide)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            list = CryptoSpinnerType.getAll(),
            currentItem = currentCryptoItem.value
        ) {
            currentCryptoItem.value = when (it.position) {
                CryptoSpinnerType.SpinnerBTC.position -> CryptoSpinnerType.SpinnerBTC
                CryptoSpinnerType.SpinnerETH.position -> CryptoSpinnerType.SpinnerETH
                CryptoSpinnerType.SpinnerADA.position -> CryptoSpinnerType.SpinnerADA
                else -> CryptoSpinnerType.SpinnerBTC
            }

        }

    }
}

@Composable
fun ChoseCryptoSpinnerWithTextFieldView() {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
    ) {
        var showDatePicker by remember { mutableStateOf(false) }
        val startGuide = createGuidelineFromStart(0.25f)

        val endGuide = createGuidelineFromStart(0.6f)

        val (text, spinner, textField) = createRefs()

        val currentCryptoItem = remember { mutableStateOf(CryptoSpinnerType.getAll()[0]) }

        Text(
            text = "加密貨幣註釋",
            modifier = Modifier.constrainAs(text) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        )

        SpinnerComponent(
            modifier = Modifier
                .constrainAs(spinner) {
                    if (showDatePicker) {
                        start.linkTo(startGuide)
                        end.linkTo(endGuide)
                        width = Dimension.fillToConstraints
                    } else {
                        start.linkTo(startGuide)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                },
            list = CryptoSpinnerType.getAll(),
            currentItem = currentCryptoItem.value
        ) {
            showDatePicker = it.position == CryptoSpinnerType.SpinnerETH.position
            currentCryptoItem.value = when (it.position) {
                CryptoSpinnerType.SpinnerBTC.position -> CryptoSpinnerType.SpinnerBTC
                CryptoSpinnerType.SpinnerETH.position -> CryptoSpinnerType.SpinnerETH
                CryptoSpinnerType.SpinnerADA.position -> CryptoSpinnerType.SpinnerADA
                else -> CryptoSpinnerType.SpinnerBTC
            }
        }

        if (showDatePicker) {
            Box(modifier = Modifier
                .constrainAs(textField) {
                    start.linkTo(endGuide, margin = 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .height(30.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(colorResource(id = R.color.black))
                .clickable {

                },
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = "註記",
                    enabled = false,
                    onValueChange = {},
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestCard(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        onClick = { /*TODO*/ }
    ) {
        SimpleText(
            modifier = Modifier.size(60.dp),
            text = "5487",
            textColor = Color.Black
        )
    }
}




