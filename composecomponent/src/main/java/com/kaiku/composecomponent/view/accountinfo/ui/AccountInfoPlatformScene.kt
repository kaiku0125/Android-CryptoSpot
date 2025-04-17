package com.kaiku.composecomponent.view.accountinfo.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.accountinfo.data.AccountInfoPlatformState

@Composable
fun AccountInfoPlatformScene(
    modifier: Modifier = Modifier,
    platformState: AccountInfoPlatformState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(38.sdp()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.sdp()))
        PocketText(
            modifier = Modifier
                .width(72.sdp())
                .height(30.sdp())
                .clipByShape(
                    shape = RoundedCornerShape(8.sdp()),
                    border = BorderStroke(1.sdp(), Color.White)
                ),
            config = PocketTextConfig(
                value = platformState.platformName,
                style = text17Sp()
            )
        )
        Spacer(modifier = Modifier.width(16.sdp()))
        Row(
            modifier = Modifier
                .weight(1f)
                .height(30.sdp())
                .clipByShape(
                    shape = RoundedCornerShape(8.sdp()),
                    border = BorderStroke(1.sdp(), Color.White)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PocketText(
                modifier = Modifier.padding(start = 12.sdp()),
                config = PocketTextConfig(
                    value = platformState.accountName,
                    style = text17Sp()
                )
            )
            PocketText(
                modifier = Modifier.padding(end = 12.sdp()),
                config = PocketTextConfig(
                    value = platformState.accountNumber,
                    style = text17Sp()
                )
            )
        }
        Spacer(modifier = Modifier.width(20.sdp()))
    }
}