package com.kaiku.composecomponent.component.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.utils.text11Sp
import com.kaiku.composecomponent.utils.text15Sp

/**
 * @param contentConfig 主內容文檔設定
 * @param hintConfig 提示文檔設定
 * @param paddingToContent 設定內容與提示文字之間的間距
 */
@Composable
fun PocketTextWithHint(
    modifier: Modifier = Modifier,
    contentConfig: PocketTextConfig = PocketTextConfig(
        value = "",
        style = text15Sp(500),
        alignment = Alignment.Center,
        textColor = Color.White
    ),
    hintConfig: PocketTextConfig = PocketTextConfig(
        value = "",
        style = text11Sp(400),
        alignment = Alignment.Center,
        textColor = Color.LightGray
    ),
    paddingToContent: Dp = 0.dp
) {
    ConstraintLayout(
        modifier = modifier
    ) {

        val (contentRegion, hintRegion) = createRefs()

        PocketText(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(contentRegion) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            config = contentConfig
        )

        PocketText(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(hintRegion) {
                    top.linkTo(contentRegion.bottom, margin = paddingToContent)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            config = hintConfig
        )

    }

}