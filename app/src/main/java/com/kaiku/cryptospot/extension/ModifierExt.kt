package com.kaiku.cryptospot.extension

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableEffect(
    noEffectWhenDisable : Boolean,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            if(noEffectWhenDisable){
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        onClick()
                    }
                )
            }else{
                Modifier.clickable(
                    onClick = {
                        onClick()
                    }
                )
            }

        )
    }
)