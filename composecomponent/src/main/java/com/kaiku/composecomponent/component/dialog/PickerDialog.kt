package com.kaiku.composecomponent.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.picker.PickerComponent
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp

@Composable
fun PickerDialog(
    isVisible: Boolean = true,
    state: Int,
    range: Iterable<Int> = -10..10,
    onConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {

    var localState by remember { mutableIntStateOf(state) }

    if (isVisible) {
        Dialog(
            onDismissRequest = {
                onDismiss.invoke()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            ),
            content = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.sdp())
                ) {


                    ConstraintLayout(
                        modifier = Modifier.background(color_333333)
                    ) {
                        val (title, divider, content, btnRow) = createRefs()

                        PocketText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.sdp())
                                .constrainAs(title) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            config = PocketTextConfig(
                                value = "Tick 設定",
                                style = text15Sp(500)
                            )
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(divider) {
                                    top.linkTo(title.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            thickness = 1.sdp(),
                            color = Color.White
                        )

                        val margin = 10.sdp()
                        PickerComponent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color_333333)
                                .padding(horizontal = 30.sdp())
                                .constrainAs(content) {
                                    top.linkTo(divider.bottom, margin = margin)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            value = localState,
                            range = range,
                            onValueChange = {
                                localState = it
                            },
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = text15Sp().fontSize,
                                fontWeight = text15Sp().fontWeight
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(btnRow) {
                                    top.linkTo(content.bottom, margin = margin)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, margin = margin)
                                }
                                .padding(horizontal = 40.sdp()),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PocketPrimaryButton(
                                modifier = Modifier.weight(1f),
                                config = PocketTextConfig(
                                    value = "取消",
                                    style = text15Sp(),
                                    textColor = MaterialTheme.colorScheme.primary
                                ),
                                primaryColor = Color.Transparent,
                                border = BorderStroke(1.sdp(), MaterialTheme.colorScheme.primary),
                                onClick = {
                                    localState = state
                                    onDismiss.invoke()
                                }
                            )

                            Spacer(modifier = Modifier.width(17.sdp()))

                            PocketPrimaryButton(
                                modifier = Modifier.weight(1f),
                                config = PocketTextConfig(
                                    value = "確定",
                                    style = text15Sp()
                                ),
                                onClick = {
                                    onConfirmed.invoke(localState.toString())
                                }
                            )
                        }

                    }
                }
            }
        )
    }

}