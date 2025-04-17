package com.kaiku.composecomponent.model.drawableProvider

import androidx.annotation.DrawableRes

open class DrawableProvider(
    @DrawableRes open val iconDelete: Int,
    @DrawableRes open val checkOn: Int,
    @DrawableRes open val checkOff: Int,
    @DrawableRes open val columnSortOn: Int,
    @DrawableRes open val columnSortOff: Int,
    @DrawableRes open val gridSortOn: Int,
    @DrawableRes open val gridSortOff: Int,
    @DrawableRes open val menuSelectOn: Int,
    @DrawableRes open val menuSelectOff: Int,
    @DrawableRes open val openAccountMain: Int,
    @DrawableRes open val openAccountTopBtn: Int,
    @DrawableRes open val openAccountBottomBtn: Int,
    @DrawableRes open val plus: Int,
    @DrawableRes open val minus: Int,
)

