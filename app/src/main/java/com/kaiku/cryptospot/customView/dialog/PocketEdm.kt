package com.kaiku.cryptospot.customView.dialog

import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kaiku.cryptospot.R
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EdmDialog(
    onNotRemind: () -> Unit,
    onDismiss: () -> Unit
) {
    var screenWidth by remember { mutableStateOf(0) }
    var screenHeight by remember { mutableStateOf(0) }

    var mainWidth by remember { mutableStateOf(0) }
    var mainHeight by remember { mutableStateOf(0) }

    var widthRatio by remember { mutableStateOf(1f) }
    var heightRatio by remember { mutableStateOf(1f) }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {}
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
//                .background(Color.Magenta)
                .onGloballyPositioned {
                    screenWidth = it.size.width
                    screenHeight = it.size.height

                    Timber.e("外框width = ${screenWidth}, 外框height = ${screenHeight}")

                }
        ) {

            val (cancel, mainBox, checkView) = createRefs()

            val bottomGuide = createGuidelineFromBottom(0.04f)

            Box(
                modifier = Modifier
                    .aspectRatio(398f / 604f) // 398 604,
                    .constrainAs(mainBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomGuide)
                    }
                    .onGloballyPositioned {
                        mainWidth = it.size.width
                        mainHeight = it.size.height

                        Timber.e("主體width = $mainWidth, 主體height = $mainHeight")

                        widthRatio = screenWidth / mainWidth.toFloat()
                        heightRatio = screenHeight / mainHeight.toFloat()
                        Timber.e("倍率width = ${widthRatio}, 倍率height = ${heightRatio}")
                    }
//                    .background(Color.Yellow)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.mipmap.bg_edm),
                    contentDescription = null
                )

                ConstraintLayout(
                    modifier = Modifier
                        .width(mainWidth.dp)
                        .height(mainHeight.dp)
                ) {
                    val startGuide = createGuidelineFromStart(0.17f)

                    val endGuide = createGuidelineFromEnd(0.15f)

                    val topGuide = createGuidelineFromTop(0.2f)

                    val item1Top = createGuidelineFromTop(0.28f)
                    val item1Bottom = createGuidelineFromTop(0.5f)

                    val item2Top = createGuidelineFromTop(0.48f)
                    val item2Bottom = createGuidelineFromTop(0.7f)

                    val item3Top = createGuidelineFromTop(0.68f)
                    val item3Bottom = createGuidelineFromTop(0.9f)

                    val (titleText, item1, item2, item3) = createRefs()


                    TitleText(
                        modifier = Modifier.constrainAs(titleText) {
                            top.linkTo(topGuide)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                        }
                    )

                    EdmItemComponent(
                        modifier = Modifier.constrainAs(item1) {
                            top.linkTo(item1Top)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                            bottom.linkTo(item1Bottom)
                        },
                        titleText = "開戶活動",
                        contentText = "抽抽抽 &8000",
                        needCloud = true,
                        needGift = false,
                    )

                    EdmItemComponent(
                        modifier = Modifier.constrainAs(item2) {
                            top.linkTo(item2Top)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                            bottom.linkTo(item2Bottom)
                        },
                        titleText = "開戶活動開戶活動",
                        contentText = "抽抽抽 &8000 君悅年菜組 ! 抽抽抽 ",
                        needCloud = false,
                        needGift = true,
                    )


                    EdmItemComponent(
                        modifier = Modifier.constrainAs(item3) {
                            top.linkTo(item3Top)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                            bottom.linkTo(item3Bottom)
                        },
                        titleText = "開戶活動開戶活動開戶活動",
                        contentText = "抽抽抽 &8000 君悅年菜組 ! 抽抽抽 &8000 君悅年菜組 ! 抽抽抽 &8000 君悅年菜組 ! ",
                        needCloud = false,
                        needGift = false,
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
                    tint = Color.Unspecified
                )
            }

            Row(
                modifier = Modifier
                    .constrainAs(checkView) {
                        top.linkTo(mainBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.Top
            ) {
                val isChecked = remember { mutableStateOf(false) }

                Checkbox(
                    modifier = Modifier.size(22.dp),
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                        if (it) {
                            onNotRemind.invoke()
                        }
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
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
    modifier: Modifier = Modifier,
    titleText: String = "開戶活動"
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

        Row(
            modifier = Modifier.padding(start = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_congratulation),
                contentDescription = null
            )
            Text(
                text = titleText,
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
    modifier: Modifier = Modifier,
    text: String = "抽抽抽 &8000 君悅年菜組 抽抽抽 &8000 君悅年菜組"
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF1F3275),
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(bottom = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFF557AFF),
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(start = 10.dp, top = 17.dp, end = 10.dp, bottom = 17.dp)
                .border(
                    width = 1.5.dp,
//                color = Color(0xFF3450B2),
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                
                Box(modifier = Modifier
                    .widthIn(max = 160.dp)
                    .heightIn(min = 36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }

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
}

@Preview
@Composable
fun EdmItemComponent(
    modifier: Modifier = Modifier,
    titleText : String = "開戶活動",
    contentText: String = "抽抽抽 &8000 君悅年菜組",
    needCloud: Boolean = true,
    needGift: Boolean = true,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (main, title, cloud, gift) = createRefs()

        val startGuide = createGuidelineFromStart(0.17f)

        val endGuide = createGuidelineFromEnd(0.15f)

        EdmItemMain(
            modifier = Modifier.constrainAs(main) {
                top.linkTo(parent.top, margin = 14.dp)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                bottom.linkTo(parent.bottom, margin = 14.dp)
                width = Dimension.fillToConstraints
            },
            text = contentText,
        )

        EdmItemTitle(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(main.top)
                    start.linkTo(main.start, margin = 20.dp)
                    bottom.linkTo(main.top)
                },
            titleText = titleText
        )

        if (needCloud) {
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(39.dp)
                    .constrainAs(cloud) {
                        top.linkTo(main.top)
                        end.linkTo(main.end)
                        start.linkTo(main.end)
                        bottom.linkTo(main.top)
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
                        bottom.linkTo(main.bottom)
                        top.linkTo(main.bottom, margin = 5.dp)
                        end.linkTo(main.end)
                    },
                painter = painterResource(id = R.drawable.ic_gift),
                contentDescription = null
            )
        }


    }
}