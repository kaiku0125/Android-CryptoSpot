package com.kaiku.cryptospot.presentation.pocket_hw

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.navigation.animation.composable
import com.kaiku.cryptospot.customView.spinner.PocketSpinner
import com.kaiku.cryptospot.customView.tab.CustomTabFillMaxWidth
import com.kaiku.cryptospot.navigation.HomeDestination
import com.kaiku.cryptospot.navigation.LoginDestination
import com.kaiku.cryptospot.navigation.NavigationEffect
import com.kaiku.cryptospot.navigation.ScreenNavigator
import com.kaiku.cryptospot.presentation.home.HomeScreen
import com.kaiku.cryptospot.utils.ScreenAnimation
import kotlinx.coroutines.launch
import timber.log.Timber

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


        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
                .fillMaxSize()
        ) {

            TabView() {
                scope.launch {
                    index = it

                }
            }

            Timber.e("index : $index")

            AnimatedVisibility(visible = index == 0) {
                if (index == 0) {
                    FirstScreen()
                }
                Timber.e("show first screen")
            }

            AnimatedVisibility(visible = index == 1) {
                if (index == 1) {
                    SecondScreen()
                }
                Timber.e("show second screen")
            }

        }
    }
}

@Composable
fun TabView(
    onTabClick: (Int) -> Unit
) {
    val index = remember { mutableStateOf(0) }

    CustomTabFillMaxWidth(
        modifier = Modifier.fillMaxWidth(),
        selectedItemIndex = index.value,
        items = listOf("一般單", "觸價單"),
        onClick = {
            index.value = it
            onTabClick.invoke(it)
        }
    )
}

@Composable
fun ValidTimeSpinnerView() {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
    ) {

        val startGuide = createGuidelineFromStart(0.25f)

        val (text, spinner) = createRefs()

        Text(
            text = "有效期",
            modifier = Modifier.constrainAs(text) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        )

        PocketSpinner(
            modifier = Modifier
                .constrainAs(spinner) {
                    start.linkTo(startGuide)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {

        }

    }
}

@Composable
fun ValidTimeWithDateSpinnerView() {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
    ) {
        var showDatePicker by remember { mutableStateOf(false) }
        val startGuide = createGuidelineFromStart(0.25f)

        val endGuide = createGuidelineFromStart(0.6f)

        val (text, spinner, textField) = createRefs()

        Text(
            text = "有效期",
            modifier = Modifier.constrainAs(text) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        )

        PocketSpinner(
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

                }

        ) {
            showDatePicker = it == "長效單"
        }

        AnimatedVisibility(
            visible = showDatePicker,
            modifier = Modifier.constrainAs(textField) {
                start.linkTo(endGuide)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        ) {

            BasicTextField(
                value = "temp",
                onValueChange = {},
                Modifier.background(Color.Blue)
            )

        }

    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun sdf() {
    NavigationEffect(
        startDestination = FirstDestination.route
    ) {
        composable(
            route = FirstDestination.route,
            enterTransition = { ScreenAnimation.screenSlideEnter(fromLeft = true) },
            exitTransition = { ScreenAnimation.screenSlideExit(toLeft = true) }
        ) {
            FirstScreen()
        }

        composable(
            route = SecondDestination.route,
            enterTransition = { ScreenAnimation.screenSlideEnter(fromLeft = false) },
            exitTransition = { ScreenAnimation.screenSlideExit(toLeft = false) }
        ) {
            SecondScreen()
        }
    }
}


