package com.kaiku.cryptospot.customView.ratiofield.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.customView.BasicType
import com.kaiku.cryptospot.customView.dialog.data.FlashRatioType
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.presentation.theme.text_15sp

@Composable
fun RatioField(
    modifier: Modifier = Modifier,
    currentItem: BasicType = FlashRatioType.getAll()[0],
    list: List<BasicType> = FlashRatioType.getAll(),
    onClick: (BasicType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        list.forEach { type ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onClick.invoke(type)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SimpleText(
                    text = stringResource(id = type.description),
                    alignment = Alignment.CenterStart
                )

                RadioButton(
                    selected = (type.position == currentItem.position),
                    onClick = {
                        onClick.invoke(type)
                    }
                )
            }

            if (type != list.last())
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.5.dp,
                    color = Color.White
                )
        }
    }
}
