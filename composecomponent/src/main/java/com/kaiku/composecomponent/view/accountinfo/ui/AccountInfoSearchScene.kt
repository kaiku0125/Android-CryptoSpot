package com.kaiku.composecomponent.view.accountinfo.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.textfield.PocketSearchField
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp

@Composable
fun ColumnScope.AccountInfoSearchScene(
    modifier: Modifier = Modifier,
    isSearchEditable: Boolean = true,
    searchText: String = "",
    onSearchTextChanged: ((String) -> Unit)? = null,
    onSearchFieldClick: (() -> Unit)? = null,
) {
    PocketSpacer(height = 8)
    PocketSearchField(
        modifier = modifier
            .height(32.sdp())
            .padding(horizontal = 12.sdp()),
        isEditable = isSearchEditable,
        textConfig = PocketTextConfig(
            value = searchText,
            style = text15Sp(),
            alignment = Alignment.CenterStart
        ),
        onTextChange = onSearchTextChanged,
        onFieldClick = onSearchFieldClick,
        onFocusChange = {}
    )
}