package com.kaiku.cryptospot.presentation.crypto_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaiku.composecomponent.component.dialog.PocketComposeDialog
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.cryptospot.customView.textfield.PocketDigitalTextFieldComponent
import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.domain.model.Holding
import com.kaiku.cryptospot.presentation.theme.CryptoSpotTheme

@Composable
fun AddUserHoldingsDialog(
    data: CryptoListingData,
    onConfirm: (Holding) -> Unit,
    onDismiss: () -> Unit
) {

    var amount by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    PocketComposeDialog(
        title = "新增${data.symbol}持倉",
        onPositiveClick = {
            if (amount.isNotEmpty() && cost.isNotEmpty()) {
                onConfirm.invoke(
                    Holding(
                        symbol = data.symbol,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        cost = cost.toDoubleOrNull() ?: 0.0
                    )
                )
            }
        },
        onNegativeClick = onDismiss,
        buttonPaddingBetween = 20.sdp(),
        content = {
            PocketSpacer(height = 20)
            PocketDigitalTextFieldComponent(
                value = amount,
                hint = "輸入數量",
                onTextChange = {
                    amount = it
                }
            )
            PocketSpacer(height = 20)
            PocketDigitalTextFieldComponent(
                value = cost,
                hint = "輸入成本",
                onTextChange = {
                    cost = it
                }
            )
            PocketSpacer(height = 20)
        }
    )

}

@Preview
@Composable
private fun AddUserHoldingsDialogPreview() {
    CryptoSpotTheme {
        AddUserHoldingsDialog(
            data = CryptoListingData.PREVIEW,
            onConfirm = {

            },
            onDismiss = {

            }
        )
    }
}