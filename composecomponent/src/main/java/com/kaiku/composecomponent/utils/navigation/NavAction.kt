package com.kaiku.composecomponent.utils.navigation

interface NavAction {

    fun back(
        route: String? = null,
        inclusive: Boolean = false,
    )

    fun to(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun replace(
        route: String,
        isSingleTop: Boolean = false,
    )

    fun offAllTo(
        route: String,
    )


}