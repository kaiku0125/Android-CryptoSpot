package com.kaiku.composecomponent.view.microcapital

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.kaiku.composecomponent.component.dialog.PocketComposeDialog
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.view.microcapital.data.MicroCapitalViewState

@Composable
fun MicroCapitalInfoDialog(
    viewState: MicroCapitalViewState,
    onPositiveClick: () -> Unit,
) {
    PocketComposeDialog(
        title = viewState.stringRes.dialogTitle,
        positiveText = viewState.stringRes.dialogPositiveBtnText,
        onPositiveClick = onPositiveClick,
        negativeText = null,
        content = {
            PocketSpacer(height = 20)
            PocketText(
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.sdp())
                    .fillMaxWidth(),
                config = PocketTextConfig(
                    value = viewState.stringRes.dialogContent,
                    style = text15Sp(),
                    textAlign = TextAlign.Center,
                    maxLines = Int.MAX_VALUE
                )
            )
            PocketSpacer(height = 28)
        }
    )
}