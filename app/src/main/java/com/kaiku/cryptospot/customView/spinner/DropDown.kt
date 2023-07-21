package com.kaiku.cryptospot.customView.spinner

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun PocketSpinner(
    list: List<String> = listOf("當日有效", "長效單")
) {
    var currentOrderText by rememberSaveable { mutableStateOf(list[0]) }
    var expanded by remember { mutableStateOf(false) }
//    var menuSize by remember { mutableStateOf(0f) }

    val onUserClick: () -> Unit = {
        expanded = expanded.not()
    }

    val offset = animateFloatAsState(
        targetValue = if (expanded) 10f else 0f,
        animationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 500
        )
    )

    val rotateAnimation = animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 300
        )
    )

    val foldAnimation = animateFloatAsState(
        targetValue = if (expanded) (20 + list.size * 30f) else 0f,
        animationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 300
        )
    )

    Column(
        modifier = Modifier
            .width(180.dp)
            .background(Color(android.graphics.Color.parseColor("#1E1E1E")))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#1E1E1E")),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onUserClick.invoke()
                    }
            ) {
                val (text, icon) = createRefs()
                Text(
                    text = currentOrderText,
                    modifier = Modifier.constrainAs(text) {
                        start.linkTo(parent.start, margin = 8.dp)
                        centerVerticallyTo(parent)
                    },
                    color = androidx.compose.ui.graphics.Color.White
                )
                IconButton(
                    modifier = Modifier.constrainAs(icon) {
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    },
                    onClick = {
                        onUserClick.invoke()
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
                    .width(180.dp)
                    .height(foldAnimation.value.dp)
                    .padding(1.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#1E1E1E")),
                        shape = RoundedCornerShape(8.dp)
                    ),
//                    .onGloballyPositioned {
//                        menuSize = (it.size.height + list.size * 2).toFloat()
//                    },
                offset = DpOffset(x = 0.dp, y = offset.value.dp)
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
                            expanded = false
                            currentOrderText = title
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
        PocketSpinner()

    }
}