package com.kaiku.cryptospot.customView.spinner

import android.graphics.Color.parseColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.common.Debug.isDropDown
import com.kaiku.cryptospot.common.recomposeHighlighter
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun PocketSpinner(
    modifier: Modifier,
    list: List<String> = listOf("當日有效", "長效單"),
    onValidTypeChange: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var spinnerWidth by remember { mutableStateOf(0.dp) }

    var currentOrderText by rememberSaveable { mutableStateOf(list[0]) }
    var expanded by remember { mutableStateOf(false) }


    val onSpinnerClick: () -> Unit = {
        expanded = expanded.not()
    }

    val calculate: (LayoutCoordinates) -> Unit = remember {
        {
            scope.launch {
                spinnerWidth = with(density) {
                    it.size.width.toDp()
                }
                if (isDropDown) Timber.e("width : $spinnerWidth")
            }
        }
    }

    val rotateAnimation = animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(
            easing = LinearEasing
        )
    )

    val foldAnimation = animateFloatAsState(
        targetValue = if (expanded) (20 + list.size * 30f) else 0f,
        animationSpec = tween(
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(parseColor("#1E1E1E")))
            .onGloballyPositioned {
                calculate.invoke(it)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .recomposeHighlighter(isDropDown)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .background(
                        color = Color(parseColor("#1E1E1E")),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onSpinnerClick.invoke()
                    }

            ) {
                val (text, icon) = createRefs()
                Text(
                    text = currentOrderText,
                    modifier = Modifier.constrainAs(text) {
                        start.linkTo(parent.start, margin = 8.dp)
                        centerVerticallyTo(parent)
                    },
                    color = Color.White
                )
                IconButton(
                    modifier = Modifier.constrainAs(icon) {
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    },
                    onClick = {
                        onSpinnerClick.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.graphicsLayer {
                            rotationZ = rotateAnimation.value
                        }
                    )

                }

            }
        }


        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(surface = Color.White),
            shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(8.dp))
        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(spinnerWidth)
                    .height(foldAnimation.value.dp)
                    .padding(1.dp)
                    .background(
                        color = Color(parseColor("#1E1E1E")),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .recomposeHighlighter(isDropDown),


//                    .onGloballyPositioned {
//                        scope.launch {
//                            menuSize = it.size.height.toFloat()
//
//                        }
//
////                        menuSize = (it.size.height + list.size * 2).toFloat()
//                    },
                offset = DpOffset(x = 0.dp, y = 10.dp)
            ) {

                list.forEach { title ->
                    DropdownMenuItem(
                        text = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = title,
                                    color = Color.White,
                                )
                            }
                        },
                        modifier = Modifier.height(30.dp),
                        onClick = {
                            onSpinnerClick.invoke()
//                            expanded = false
                            currentOrderText = title
                            onValidTypeChange.invoke(currentOrderText)
                        },
                    )
                }


            }

        }


    }
}


@Preview(showBackground = true)
@Composable
private fun MenuSample_Preview() {
    MaterialTheme {
        PocketSpinner(modifier = Modifier) {

        }

    }
}