package com.kaiku.cryptospot.extension

import android.content.Context
import android.media.AudioManager
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

fun (() -> Unit).withEffect(
    context: Context,
    haptic: HapticFeedback,
    needSound: Boolean = true,
    needHaptic: Boolean = true,
): () -> Unit = {
    if (needSound) {
        (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
            .playSoundEffect(AudioManager.FX_KEY_CLICK)
    }

    if (needHaptic) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    this()
}