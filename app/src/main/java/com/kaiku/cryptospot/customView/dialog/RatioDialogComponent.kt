package com.kaiku.cryptospot.customView.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kaiku.cryptospot.customView.BasicType
import com.kaiku.cryptospot.customView.dialog.data.FlashRatioType
import com.kaiku.cryptospot.customView.ratiofield.data.RatioField
import com.kaiku.cryptospot.customView.text.SimpleText

@Composable
fun RatioDialogComponent(
    modifier : Modifier = Modifier,
    title: String = "標題",
    currentItem: BasicType = FlashRatioType.getAll()[0],
    list: List<BasicType> = FlashRatioType.getAll(),
    onClick: (BasicType) -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        )
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            SimpleText(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                text = title,
            )

            RatioField(
                currentItem = currentItem,
                list = list,
                onClick = {
                    onClick.invoke(it)
                }
            )

        }
    }
}

