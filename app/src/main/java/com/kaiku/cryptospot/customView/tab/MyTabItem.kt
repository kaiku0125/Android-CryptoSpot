package com.kaiku.cryptospot.customView.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.BasicType
import com.kaiku.cryptospot.customView.tab.data.BadgeTabType
import com.kaiku.cryptospot.extension.clickableEffectConfig
import com.kaiku.cryptospot.extension.data.ClickableConfig

@Composable
fun TabItem(
    isSelected: Boolean,
    shape: Shape,
    tabWidth: Dp,
    type: BasicType,
    onClick: (BasicType) -> Unit
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Black
        } else {
            White
        },
        animationSpec = tween(easing = LinearEasing),
        label = ""
    )
    Text(
        modifier = Modifier
            .clip(shape)
            .clickable {
                onClick.invoke(type)
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        text = stringResource(id = type.description),
        color = tabTextColor,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun TabItemWithBadge(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    shape: Shape,
    tabWidth: Dp,
    type: BasicType,
    isBadgeLight: Boolean,
    isEnable: Boolean,
    onClick: (BasicType) -> Unit
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Black
        } else {
            White
        },
        animationSpec = tween(easing = LinearEasing),
        label = ""
    )

    Box(
        modifier = modifier
            .clip(shape)
            .width(tabWidth)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clickableEffectConfig(
                config = ClickableConfig(
                    needRipple = false
                ),
                onClick = {
                    if(isEnable) {
                        onClick.invoke(type)
                    }
                }
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.width(tabWidth),
                text = stringResource(id = type.description),
                textAlign = TextAlign.Center,
                color = if (isEnable) tabTextColor else Color.Gray,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            )
        }

        if (isBadgeLight) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = tabWidth / 8, end = tabWidth / 8)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .size(5.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TabItemWithBadgePreview() {
    TabItemWithBadge(
        modifier = Modifier.height(30.dp),
        isSelected = false,
        shape = RoundedCornerShape(8.dp),
        tabWidth = 45.dp,
        type = BadgeTabType.TabOne,
        isBadgeLight = true,
        isEnable = true,
        onClick = {}
    )
}


