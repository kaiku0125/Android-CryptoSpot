package com.kaiku.composecomponent.view.financialstatement.data


data class FinancialDetailViewState(
    val title: String,
    val meetingTime: String,
    val items: List<FinancialDetailItem>
)

data class FinancialDetailItem(
    val title: String,
    val content: String
)

