package com.kaiku.cryptospot.presentation.pocket_hw

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.spinner.SpinnerComponent
import com.kaiku.cryptospot.customView.spinner.data.CryptoSpinnerType
import com.kaiku.cryptospot.customView.tab.CustomTabFillMaxWidth
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideEnter
import com.kaiku.cryptospot.utils.ScreenAnimation.screenSlideExit
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketHomeworkScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "titleScreen",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            ScreenNavigator.back(HomeDestination.route)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }

                },
            )

        }
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        var index by remember { mutableStateOf(0) }


        ConstraintLayout(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
                .fillMaxSize()
        ) {

            val (tabRegion, firstRegion, secondRegion) = createRefs()

            TabView(
                modifier = Modifier.constrainAs(tabRegion) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
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
                modifier = Modifier.constrainAs(firstRegion){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tabRegion.bottom)
                }

            ) {
                FirstTabSpinnerView()
            }

            AnimatedVisibility(
                visible = index == 1,
                enter = screenSlideEnter(fromLeft = false),
                exit = screenSlideExit(toLeft = false),
                modifier = Modifier.constrainAs(secondRegion){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tabRegion.bottom)
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
    val index = remember { mutableStateOf(0) }

    CustomTabFillMaxWidth(
        modifier = modifier.fillMaxWidth(),
        selectedItemIndex = index.value,
        items = listOf("一般單", "觸價單"),
        onClick = {
            index.value = it
            onTabClick.invoke(it)
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


