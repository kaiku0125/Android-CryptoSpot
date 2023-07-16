package com.kaiku.cryptospot.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

object ScreenAnimation {

    fun screenSlideEnter(fromLeft: Boolean = true): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = {
                if (fromLeft)
                    -300
                else
                    300
            },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300))
    }

    fun screenSlideExit(toLeft: Boolean = true): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = {
                if (toLeft)
                    -300
                else
                    300
            },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    }

}