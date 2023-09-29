package com.kaiku.cryptospot.customView.text

import androidx.annotation.ColorRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.presentation.theme.text_11sp
import com.kaiku.cryptospot.presentation.theme.text_15sp

@Composable
fun SimpleTextWithHint(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    hintAlignment: Alignment = Alignment.Center,
    content: String,
    hint: String,
    contentStyle: TextStyle = MaterialTheme.typography.text_15sp,
    hintStyle : TextStyle = MaterialTheme.typography.text_11sp,
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
            alignment = contentAlignment,
            text = content,
            style = contentStyle,
            textColor = contentTextColor
        )

        SimpleText(
            modifier = Modifier.constrainAs(hintRegion){
                top.linkTo(contentRegion.bottom)
                start.linkTo(parent.start)
            },
            alignment = hintAlignment,
            text = hint,
            style = hintStyle,
            textColor = hintTextColor
        )

    }

}