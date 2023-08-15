package com.kaiku.cryptospot.customView.dialog

import android.graphics.Color.parseColor
import android.widget.CheckBox
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.text.TextWithIcon
import timber.log.Timber


private val ITEM_HEIGHT = 77.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EdmDialog(
    onNotRemind: () -> Unit,
    onDismiss: () -> Unit
) {
    var screenWidth by remember { mutableStateOf(0) }
    var screenHeight by remember { mutableStateOf(0) }

    var mainWidth by remember{ mutableStateOf(0) }
    var mainHeight by remember{ mutableStateOf(0) }

    var widthRatio by remember { mutableStateOf(1f) }
    var heightRatio by remember { mutableStateOf(1f) }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {}
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
                .onGloballyPositioned {
                    screenWidth = it.size.width
                    screenHeight = it.size.height

                    Timber.e("外框width = ${screenWidth}, 外框height = ${screenHeight}")

                }
        ) {

            val (cancel, mainBox, checkView) = createRefs()

            Box(
                modifier = Modifier
                    .aspectRatio(398f / 604f) // 398 604,
                    .constrainAs(mainBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .onGloballyPositioned {
                        mainWidth = it.size.width
                        mainHeight = it.size.height

                        Timber.e("主體width = $mainWidth, 主體height = $mainHeight")

                        widthRatio = screenWidth / mainWidth.toFloat()
                        heightRatio = screenHeight / mainHeight.toFloat()
                        Timber.e("倍率width = ${widthRatio}, 倍率height = ${heightRatio}")
                    }
                    .background(Color.Yellow)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.mipmap.bg_edm),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = ((screenHeight - mainHeight) / 2).dp,
//                            top = 130.dp,
                            start = 20.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleText()
                    Spacer(modifier = Modifier.height((10 * heightRatio).dp))

                    EdmItemComponent(
                        needCloud = false,
                        needGift = false
                    )
                    Spacer(modifier = Modifier.height((15 * heightRatio).dp))
                    EdmItemComponent(
                        needCloud = true,
                        needGift = false
                    )
                    Spacer(modifier = Modifier.height((15 * heightRatio).dp))
                    EdmItemComponent(
                        needCloud = false,
                        needGift = true
                    )
                }

            }

            IconButton(
                modifier = Modifier.constrainAs(cancel) {
                    top.linkTo(mainBox.top)
                    end.linkTo(parent.end)
                },
                onClick = { onDismiss.invoke() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier
                    .constrainAs(checkView) {
                        top.linkTo(mainBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isChecked = remember { mutableStateOf(false) }

                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                        if (it) {
                            onNotRemind.invoke()
                        }
                    }
                )
                Text(text = "今日不再顯示")
            }

        }


    }
}

@Preview
@Composable
private fun TitleText(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(207.dp)
            .height(46.dp)
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.extraLarge
            )
            .border(
                width = 2.dp,
                color = Color(0xFF1F378D),
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(
                    color = Color(parseColor("#D8E1FF")),
                    shape = MaterialTheme.shapes.extraLarge
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "5月活動通知",
                style = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF1F378D),
                )
            )
        }
    }
}


@Preview
@Composable
private fun EdmItemTitle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(27.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 100.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF3450B2),
                shape = RoundedCornerShape(size = 100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        TextWithIcon(
            modifier = modifier.padding(5.dp),
            drawableStart = R.drawable.ic_congratulation
        ) {
            Text(
                text = "開戶活動",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF3450B2),
                )
            )
        }
    }

}

@Preview
@Composable
private fun EdmItemMain(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(279.dp)
            .height(ITEM_HEIGHT)
            .background(color = Color(0xFF557AFF), shape = RoundedCornerShape(size = 15.dp))
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 20.dp)
//            .shadow(
//                elevation = 1.dp,
//                spotColor = Color(0xFF1F3275),
//                ambientColor = Color(0xFF1F3275)
//            )
            .border(
                width = 1.5.dp,
                color = Color(0xFF3450B2),
                shape = RoundedCornerShape(size = 15.dp)
            )
    ) {

        Row(
            modifier = Modifier
                .background(
                    color = Color.Cyan,
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "抽抽抽 &8000 君悅年菜組！",
                maxLines = 2,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                )
            )

            Box(
                modifier = Modifier
                    .width(58.dp)
                    .height(26.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "詳情",
                    fontSize = 11.sp,
                    color = Color(0xFF3450B2)
                )
            }
        }
    }
}

@Preview
@Composable
fun EdmItemComponent(
    modifier: Modifier = Modifier,
    needCloud: Boolean = true,
    needGift: Boolean = true
) {
    ConstraintLayout(
        modifier = Modifier.height(ITEM_HEIGHT + 30.dp)
    ) {
        val (title, main, cloud, gift) = createRefs()

        EdmItemMain(
            modifier = Modifier.constrainAs(main) {
                top.linkTo(parent.top, margin = 14.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end, margin = 14.dp)
                bottom.linkTo(parent.bottom, margin = 14.dp)
            }
        )

        EdmItemTitle(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 20.dp)
                }
        )

        if (needCloud) {
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(37.dp)
                    .constrainAs(cloud) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_cloud),
                contentDescription = null
            )
        }

        if (needGift) {
            Image(
                modifier = Modifier
                    .width(35.dp)
                    .height(32.dp)
                    .constrainAs(gift) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 14.dp)
                    },
                painter = painterResource(id = R.drawable.ic_gift),
                contentDescription = null
            )
        }


    }
}