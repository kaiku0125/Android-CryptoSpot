package com.kaiku.composecomponent.view.menu.menuallfunction.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_252525
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_49454f
import com.kaiku.composecomponent.color_717071
import com.kaiku.composecomponent.color_ca1039
import com.kaiku.composecomponent.component.button.PocketRotationIconButton
import com.kaiku.composecomponent.component.grid.data.HomeMenuGridBaseItem
import com.kaiku.composecomponent.component.image.PocketAsyncImage
import com.kaiku.composecomponent.component.image.PocketAsyncImageConfig
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.pocket_color_ffffff
import com.kaiku.composecomponent.utils.localDrawableProvider
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text11Sp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.view.menu.MenuItem
import com.kaiku.composecomponent.view.menu.menuallfunction.data.BADGE_TYPE_NEW
import com.kaiku.composecomponent.view.menu.menuallfunction.data.BADGE_TYPE_UNRECEIVED
import com.kaiku.composecomponent.view.menu.menuallfunction.data.HomeMenuGroupLocalItemStructure
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuAllFunctionViewState
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuGroupStructure
import com.kaiku.composecomponent.view.menu.menuallfunction.data.MenuIconType
import com.kaiku.composecomponent.view.menu.menuallfunction.data.ViewModelAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalPagerApi::class)
fun MenuAllFunctionPager(
    viewState: MenuAllFunctionViewState,
    isExpand: Boolean,
    showAllTitleIcon: () -> Unit,
    isColumn: Boolean,
    sortIconClick: () -> Unit,
    viewModelAction: (ViewModelAction) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = viewState.homeMenuGroupCompleteItemList.size,
        infiniteLoop = true
    )

    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    val scrollToPage: (String) -> Unit = { title ->
        coroutineScope.launch {
            pagerState.animateScrollToPage(viewState.homeMenuGroupCompleteItemList.indexOfFirst {
                it.name == title
            })
        }
    }

    PagerTab(tabIndex, viewState, coroutineScope, pagerState, isExpand, showAllTitleIcon)

    PagerContent(
        viewState = viewState,
        isColumn = isColumn,
        sortIconCLick = sortIconClick,
        pagerState = pagerState,
        viewModelAction = viewModelAction,
        isExpand = isExpand,
        tabIndex = tabIndex,
        scrollToPage = scrollToPage
    )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerTab(
    tabIndex: Int,
    viewState: MenuAllFunctionViewState,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    isExpand: Boolean,
    showAllTitleIcon: () -> Unit
) {
    Row(modifier = Modifier
        .background(color_252525)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        ScrollableTabRow(
            modifier = Modifier
                .weight(1f),
            containerColor = color_252525,
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            edgePadding = 0.dp
        ) {
            viewState.homeMenuGroupCompleteItemList.forEachIndexed { index, homeMenuNewPage ->
                Tab(
                    modifier = Modifier
                        .requiredHeight(36.sdp())
                        .background(color_252525),
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = homeMenuNewPage.name,
                            style = text15Sp(),
                            color = if (tabIndex == index) {
                                pocket_color_ffffff
                            } else {
                                color_717071
                            },
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier.size(36.sdp()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PocketRotationIconButton(
                modifier = Modifier.weight(1f).background(color_252525),
                isExpand = isExpand,
                iconSize = 36.dp,
                imageResource = R.drawable.pocket_ic_arrow_down,
                tint = pocket_color_ffffff,
                clickableConfig = ClickableConfig(),
                onIconClick = {
                    showAllTitleIcon.invoke()
                }
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = color_49454f
            )
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun PagerContent(
    drawableProvider: DrawableProvider = localDrawableProvider(),
    viewState: MenuAllFunctionViewState,
    isColumn: Boolean,
    sortIconCLick: () -> Unit,
    pagerState: PagerState,
    viewModelAction: (ViewModelAction) -> Unit,
    isExpand: Boolean,
    tabIndex: Int,
    scrollToPage: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Crossfade(
            targetState = isExpand,
            label = ""
        ) { isExpand ->

            Column(modifier = Modifier.fillMaxSize()) {
                if (viewState.isLocked) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color_252525)
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "顯示排序：")
                        Icon(
                            painter = painterResource(
                                id = if (isColumn)
                                    drawableProvider.columnSortOn
                                else
                                    drawableProvider.columnSortOff
                            ),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.clickable(onClick = sortIconCLick)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(
                                id = if (isColumn)
                                    drawableProvider.gridSortOff
                                else
                                    drawableProvider.gridSortOn
                            ),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.clickable(onClick = sortIconCLick)
                        )
                    }
                }
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxHeight(),
                    state = pagerState,
                ) { index ->
                    viewState.homeMenuGroupCompleteItemList.getOrNull(index)
                        ?.let { homeMenuGroupCompleteItem ->
                            PagerSortContent(
                                isLocked = viewState.isLocked,
                                isColumn = isColumn,
                                homeMenuGridBaseItem = viewState.homeMenuGridBaseItemList,
                                homeMenuGroupCompleteItem = homeMenuGroupCompleteItem,
                                viewModelAction = viewModelAction
                            )
                        }
                }
            }

            if (isExpand) {
                CustomRadioGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color_252525)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    tabIndex = tabIndex,
                    homeMenuNewPageList = viewState.homeMenuGroupCompleteItemList,
                    action = scrollToPage
                )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomRadioGroup(
    modifier: Modifier = Modifier,
    homeMenuNewPageList: List<HomeMenuGroupLocalItemStructure>,
    action: (String) -> Unit,
    tabIndex: Int
) {
    var selectedOption by remember(tabIndex) {
        mutableStateOf(homeMenuNewPageList[tabIndex].name)
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
        action(text)
    }

    FlowRow(
        modifier = modifier
            .clickableEffectConfig {},
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        homeMenuNewPageList.forEach {
            val text = it.name
            PocketTextWithClickEffect(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (text == selectedOption) {
                                Color.White
                            } else {
                                color_333333
                            },
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(
                        color = if (text == selectedOption) {
                            Color.White
                        } else {
                            color_252525
                        }
                    ),
                textModifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                config = PocketTextConfig(
                    value = text,
                    textColor = if (text == selectedOption) {
                        color_252525
                    } else {
                        pocket_color_ffffff
                    },
                    style = text15Sp(),
                    maxLines = 1
                ),
                onClick = {
                    onSelectionChange(text)

                }
            )

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun PagerSortContent(
    homeMenuGroupCompleteItem: HomeMenuGroupLocalItemStructure,
    homeMenuGridBaseItem: List<HomeMenuGridBaseItem>,
    isColumn: Boolean = false,
    isLocked: Boolean,
    viewModelAction: (ViewModelAction) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (!isLocked || isColumn) {
            ColumnMenuIconLayout(
                isLockedMode = isLocked,
                homeMenuGridBaseItem = homeMenuGridBaseItem,
                homeMenuStructureList = homeMenuGroupCompleteItem.items,
                viewModelAction = viewModelAction
            )
        } else {
            GridMenuIconLayout(
                homeMenuStructureList = homeMenuGroupCompleteItem.items,
                viewModelAction = viewModelAction
            )
        }
    }
}

@Composable
private fun GridMenuIconLayout(
    homeMenuStructureList: List<MenuGroupStructure>,
    viewModelAction: (ViewModelAction) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5), content = {
            itemsIndexed(homeMenuStructureList) { _, structure ->
                MenuItem(
                    titleConfig = PocketTextConfig(
                        value = structure.title,
                        style = text13Sp(),
                        maxLines = 1
                    ),
                    menuIconType = structure.menuIconType,
                    drawableRes = structure.iconResId,
                    iconUrl = structure.iconUrl,
                    onClick = {
                        viewModelAction(ViewModelAction.ClickHomeMenuItem(structure.key))
                    }
                )
            }
        }
    )
}

@Composable
private fun ColumnMenuIconLayout(
    drawableProvider: DrawableProvider = localDrawableProvider(),
    homeMenuStructureList: List<MenuGroupStructure>,
    homeMenuGridBaseItem: List<HomeMenuGridBaseItem>,
    isLockedMode: Boolean = false,
    viewModelAction: (ViewModelAction) -> Unit
) {
    LazyColumn(content = {
        itemsIndexed(homeMenuStructureList) { index, item ->
            val isItemLocked = homeMenuGridBaseItem.find {
                it.id == item.key
            }?.isLocked

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isLockedMode) {
                        viewModelAction(ViewModelAction.ClickHomeMenuItem(item.key))
                    } else {
                        if (isItemLocked != true) {
                            viewModelAction(ViewModelAction.ModifyItem(item.key))
                        } else {
                            viewModelAction(ViewModelAction.None)
                        }
                    }
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.iconUrl.isNotBlank()) {
                    PocketAsyncImage(
                        modifier = Modifier.size(30.dp),
                        imageConfig = PocketAsyncImageConfig(
                            url = item.iconUrl,
                            errorDrawable = item.iconResId
                        )
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = item.iconResId),
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title)
                Spacer(modifier = Modifier.width(8.dp))

                if (item.menuIconType.badgeType == BADGE_TYPE_UNRECEIVED) {
                    Image(painterResource(id = R.drawable.pocket_home_menu_badge_2), "")
                } else if (item.menuIconType.current != null && item.menuIconType.current > 0) {
                    Box(modifier = Modifier
                        .background(shape = RoundedCornerShape(100.sdp()), color = color_ca1039)
                        .padding(horizontal = 4.sdp()),
                        contentAlignment = Alignment.Center) {
                        val display =
                            if (item.menuIconType.max < item.menuIconType.current) "${item.menuIconType.max}+" else item.menuIconType.current
                        Text(
                            text = display.toString(),
                            color = pocket_color_ffffff,
                            style = text11Sp()
                        )
                    }
                } else if (item.menuIconType.badgeType == BADGE_TYPE_NEW) {
                    Image(painterResource(id = R.drawable.pocket_home_menu_badge_1), "")
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isLockedMode.not() && isItemLocked != true) {
                    IconToggleButton(modifier = Modifier.heightIn(max = 30.dp),
                        checked = homeMenuGridBaseItem.any {
                            it.id == item.key
                        },
                        onCheckedChange = {
                            viewModelAction(
                                ViewModelAction.ModifyItem(item.key)
                            )
                        })
                    {
                        Icon(
                            painter = painterResource(if (homeMenuGridBaseItem.any {
                                    it.id == item.key
                                }) drawableProvider.menuSelectOn else drawableProvider.menuSelectOff),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            if (index < homeMenuStructureList.lastIndex)
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = color_333333
                )
        }
    })
}

@Composable
@Preview
fun PreviewPagerContent() {
    PagerSortContent(
        homeMenuGroupCompleteItem = HomeMenuGroupLocalItemStructure(
            id = "123",
            name = "title",
            items = listOf(
                MenuGroupStructure(
                    menuIconType = MenuIconType(
                        badgeType = BADGE_TYPE_NEW,
                        current = 100
                    ),
                    iconUrl = "",
                    key = "more",
                    title = "更多功能",
                    iconResId = R.drawable.pocket_ic_delete_tw
                )
            )
        ),isColumn = true, isLocked = true, viewModelAction = {}, homeMenuGridBaseItem = emptyList()
    )
}