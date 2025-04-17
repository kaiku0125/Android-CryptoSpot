package com.kaiku.composecomponent.view.microcapital.data

data class MicroCapitalStringRes(
    var title: String = "小資方案",
    var participateDescription: String = "參加中",
    var participateReverseDescription: String = "已登記取消",
    var participateReverseBtnText: String = "取消方案",
    var cancelDescription: String = "已取消",
    var cancelReverseDescription: String = "已重新登記",
    var cancelReverseBtnText: String = "取消登記",
    var titleUserStatus: String = "登記狀態",
    var titleRegistrationDate: String = "登記日期",
    var titleEffectiveDate: String = "生效日期",
    var titleTwTradeAmount: String = "台股交易額",
    var titleTwTradeCount: String = "台股成交筆數",
    var titleTwTradeFee: String = "手續費",
    var titleUsTradeAmount: String = "美股交易額",
    var titleUsTradeCount: String = "美股成交筆數",
    var titleUsTradeFee: String = "手續費",
    var titleHint: String = "（當月）",
    var moreInfo: String = "了解詳情",
    var reRegister: String = "重新登記 ↗",
    var dialogTitle: String = "小資方案",
    var dialogContent: String = "小資方案註解文案小資方案註解文案小資方案註解文案小資方案註解文案小資方案註解文案小資方案註解文案小資方案註解文案小資方案註解文案",
    var dialogPositiveBtnText: String = "關閉",
) {
    companion object {
        val DEFAULT = MicroCapitalStringRes()
    }
}
