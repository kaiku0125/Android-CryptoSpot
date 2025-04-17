package com.kaiku.composecomponent.component.grid.data

import androidx.annotation.DrawableRes

abstract class HomeMenuGridBaseItem(
    open val id: String,
    open val name: String,
    @DrawableRes open val iconResId: Int,
    open val iconUrl: String? = null,
    open val isLocked: Boolean,
    open val gaSelectKey: String?
)

data class HomeMenuGridItem(
    override val id: String,
    override val name: String,
    @DrawableRes override val iconResId: Int,
    override val iconUrl: String? = null,
    override val isLocked: Boolean,
    override val gaSelectKey: String?
) : HomeMenuGridBaseItem(id, name, iconResId, iconUrl, isLocked, gaSelectKey)