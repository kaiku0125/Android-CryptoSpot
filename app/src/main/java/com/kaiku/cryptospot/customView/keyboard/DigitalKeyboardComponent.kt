package com.kaiku.cryptospot.customView.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.extension.toStyleOfDigitalKeyBoard
import kotlinx.coroutines.launch

private val ITEM_HEIGHT = 60.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalKeyboardComponent(
    modifier: Modifier = Modifier,
    text : String = "54879487",
    sheetState: SheetState,
    onConfirm: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest.invoke()
        }
    ) {
        DigitalKeyboardContent(
            modifier = modifier,
            text = text,
            onConfirm = {
                onConfirm.invoke(it)
            }
        )
    }
}

@Composable
private fun DigitalKeyboardContent(
    modifier: Modifier = Modifier,
    text: String,
    onConfirm: (String) -> Unit
) {

//    val vm : viewMOdel = viewModel()
    val scope = rememberCoroutineScope()

    var value by remember { mutableStateOf(text) }

    val textChangedLambda = remember<(String) -> Unit>
    {
        {
            scope.launch {
                value = buildString {
                    append("${value}${it}")
                }
            }
        }
    }

    val backPressLambda = remember<() -> Unit>
    {
        {
            scope.launch {
                if (value.isNotEmpty())
                    value = value.substring(0, value.length - 1)
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 3.dp)
    ) {

        val (editText) = createRefs()

        val (one, two, three) = createRefs()

        val (four, five, six) = createRefs()

        val (seven, eight, nine) = createRefs()

        val (zero, clear, dot, backPress, confirm) = createRefs()

        val (spacer) = createRefs()

        val line25 = createGuidelineFromStart(0.25f)

        val line50 = createGuidelineFromStart(0.5f)

        val line75 = createGuidelineFromStart(0.75f)


        Box(
            modifier = remember {
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .constrainAs(editText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            },
            contentAlignment = Alignment.Center
        ) {
            SimpleText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp, vertical = 8.dp)
                    .toStyleOfDigitalKeyBoard(
                        height = 50.dp,
                        background = Color.DarkGray
                    ),
                text = value
            )
        }

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(one) {
                        top.linkTo(editText.bottom, margin = 5.dp)
                        start.linkTo(parent.start, margin = 3.dp)
                        end.linkTo(line25, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("1")
                    }
            },
            text = "1"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(two) {
                        top.linkTo(editText.bottom, margin = 5.dp)
                        start.linkTo(line25, margin = 3.dp)
                        end.linkTo(line50, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("2")
                    }
            },
            text = "2"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(three) {
                        top.linkTo(editText.bottom, margin = 5.dp)
                        start.linkTo(line50, margin = 3.dp)
                        end.linkTo(line75, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("3")
                    }
            },
            text = "3"
        )

        Box(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(backPress) {
                        top.linkTo(editText.bottom, margin = 5.dp)
                        start.linkTo(line75, margin = 3.dp)
                        end.linkTo(parent.end, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        backPressLambda.invoke()
                    }
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(four) {
                        top.linkTo(one.bottom, margin = 5.dp)
                        start.linkTo(parent.start, margin = 3.dp)
                        end.linkTo(line25, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("4")
                    }
            },
            text = "4"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(five) {
                        top.linkTo(two.bottom, margin = 5.dp)
                        start.linkTo(line25, margin = 3.dp)
                        end.linkTo(line50, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("5")
                    }
            },
            text = "5"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(six) {
                        top.linkTo(three.bottom, margin = 5.dp)
                        start.linkTo(line50, margin = 3.dp)
                        end.linkTo(line75, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("6")
                    }
            },
            text = "6"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(seven) {
                        top.linkTo(four.bottom, margin = 5.dp)
                        start.linkTo(parent.start, margin = 3.dp)
                        end.linkTo(line25, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("7")
                    }
            },
            text = "7"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(eight) {
                        top.linkTo(five.bottom, margin = 5.dp)
                        start.linkTo(line25, margin = 3.dp)
                        end.linkTo(line50, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("8")
                    }
            },
            text = "8"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(nine) {
                        top.linkTo(six.bottom, margin = 5.dp)
                        start.linkTo(line50, margin = 3.dp)
                        end.linkTo(line75, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("9")
                    }
            },
            text = "9"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(clear) {
                        top.linkTo(seven.bottom, margin = 5.dp)
                        start.linkTo(parent.start, margin = 3.dp)
                        end.linkTo(line25, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        value = ""
                    }
            },
            text = "清除"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(zero) {
                        top.linkTo(eight.bottom, margin = 5.dp)
                        start.linkTo(line25, margin = 3.dp)
                        end.linkTo(line50, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke("0")
                    }
            },
            text = "0"
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(dot) {
                        top.linkTo(nine.bottom, margin = 5.dp)
                        start.linkTo(line50, margin = 3.dp)
                        end.linkTo(line75, margin = 3.dp)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        textChangedLambda.invoke(".")
                    }
            },
            text = "."
        )

        Spacer(
            modifier = remember {
                Modifier
                    .height(35.dp)
                    .constrainAs(spacer) {
                        top.linkTo(clear.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            }
        )

        SimpleText(
            modifier = remember {
                Modifier
                    .toStyleOfDigitalKeyBoard()
                    .constrainAs(confirm) {
                        top.linkTo(backPress.bottom, margin = 5.dp)
                        start.linkTo(line75, margin = 3.dp)
                        end.linkTo(parent.end, margin = 3.dp)
                        bottom.linkTo(spacer.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .clickable {
                        onConfirm.invoke(value)
                    }
            },
            text = "確認"
        )

    }

}


data class KeyBoardViewState(
    val isShown: Boolean = false,
)

