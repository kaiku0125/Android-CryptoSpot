package com.kaiku.cryptospot.customView.tab

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun PocketTab(
    list: List<String> = listOf("一般單", "觸價單"),
    initIndex: Int = 0,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onSwitch: (String) -> Unit
) {
    val density = LocalDensity.current
    val nowIndex by remember { mutableStateOf(initIndex) }

    var componentWidth by remember { mutableStateOf(0) }


//    val offset by animateDpAsState(
//        targetValue = (nowIndex * oneItemWidth).dp,
//        animationSpec = animationSpec
//    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
//                .width(componentWidth.dp)
                .fillMaxHeight()
                .background(Color.Green)
        ) {

        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .onGloballyPositioned {
                    componentWidth = with(density) {
                        it.size.height
                    }
                }
        ) {

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {

                    },
                contentAlignment = Alignment.Center,
            ) {

                Text(
                    text = "一般單",
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "觸價單",
                    color = Color.White
                )

            }
        }
        Timber.e("width : $componentWidth")



    }


}

@Preview(showBackground = true)
@Composable
private fun PocketTabPreview() {
    PocketTab {

    }
}