package com.kaiku.cryptospot.customView.text

import androidx.annotation.ColorRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.presentation.theme.text_11sp_400weight
import com.kaiku.cryptospot.presentation.theme.text_15sp_400weight


@Composable
fun SimpleTextWithHint(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    hintAlignment: Alignment = Alignment.Center,
    content: String,
    hint: String,
    contentStyle: TextStyle = MaterialTheme.typography.text_15sp_400weight,
    hintStyle : TextStyle = MaterialTheme.typography.text_11sp_400weight,
    @ColorRes contentTextColor: Color = Color.White,
    @ColorRes hintTextColor: Color = Color.LightGray,
) {
    ConstraintLayout(
        modifier = modifier
    ) {

        val (contentRegion, hintRegion) = createRefs()

        SimpleText(
            modifier = Modifier.constrainAs(contentRegion) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            config = SimpleTextConfig(
                value = content,
                alignment = contentAlignment,
                style = contentStyle,
                textColor = contentTextColor
            )
        )

        SimpleText(
            modifier = Modifier.constrainAs(hintRegion){
                top.linkTo(contentRegion.bottom)
                start.linkTo(parent.start)
            },
            config = SimpleTextConfig(
                value = hint,
                alignment = hintAlignment,
                style = hintStyle,
                textColor = hintTextColor
            )
        )

    }

}