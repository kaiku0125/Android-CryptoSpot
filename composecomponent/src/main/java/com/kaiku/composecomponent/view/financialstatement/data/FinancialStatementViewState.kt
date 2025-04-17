package com.kaiku.composecomponent.view.financialstatement.data

data class FinancialStatementViewState(
    val isNewVisible: Boolean,
    val overviewState: OverviewState,
    val historyItems: List<HistoryItem>
) {
    companion object {
        val INIT = FinancialStatementViewState(
            isNewVisible = true,
            overviewState = OverviewState(
                historyItem = HistoryItem(
                    year = "",
                    season = "",
                    date = ""
                ),
                time = "",
                content = ""
            ),
            historyItems = emptyList()
        )
    }
}

data class OverviewState(
    val historyItem: HistoryItem,
    val time: String,
    val content: String
) {
    fun toTitleDisplay(): String = "${historyItem.year} ${historyItem.season} - 會議重點"

}

data class HistoryItem(
    val year: String,
    val season: String,
    val date: String
) {
    fun toDisplay(): String = "$year $season 財報會議"
}
