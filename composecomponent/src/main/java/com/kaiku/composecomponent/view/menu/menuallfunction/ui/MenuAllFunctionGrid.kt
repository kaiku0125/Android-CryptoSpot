package com.kaiku.composecomponent.view.menu.menuallfunction.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.grid.LocalHomeMenuReorderGrid
import com.kaiku.composecomponent.pocket_color_ffffff
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuAllFunctionViewState
import com.kaiku.composecomponent.view.menu.menuallfunction.data.ViewModelAction

@Composable
fun MenuAllFunctionReorderGrid(
    viewState: MenuAllFunctionViewState,
    viewModelAction: (ViewModelAction) -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color_252525)
                .padding(vertical = 8.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "釘選首頁捷徑",
                style = text15Sp(500),
                color = pocket_color_ffffff
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "最少釘選4個功能顯示於首頁",
                style = text13Sp(),
                color = color_9e9e9f
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = color_414141
        )

        if (viewState.homeMenuGridBaseItemList.isNotEmpty()) {
            LocalHomeMenuReorderGrid(
                homeMenuGridBaseItemList = viewState.homeMenuGridBaseItemList,
                updateSortAction = {
                    viewModelAction(
                        ViewModelAction.UpdateHomeMenuItem(it)
                    )
                },
                onClick = {
                    viewModelAction(
                        ViewModelAction.RemoveHomeMenuItem(it)
                    )
                }
            )
        }
    }
}