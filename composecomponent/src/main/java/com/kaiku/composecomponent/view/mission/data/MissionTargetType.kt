package com.kaiku.composecomponent.view.mission.data

enum class MissionTargetType(
    val key: String,
    val title: String
) {
    TRADE_AMOUNT(
        key = "trade_amount",
        title = "交易金額"
    ), // 交易金額
    TRADE_TIMES(
        key = "trade_times",
        title = "交易次數"
    ), // 交易次數
    RECOMMEND_COUNT(
        key = "recommend_count",
        title = "推薦人數"
    ), // 推薦人數
    MGM_TRADE_AMT(
        key = "mgm_trade_amt",
        title = "分享對象交易金額"
    ), // 分享對象交易金額
    MGM_TRADE_TIMES(
        key = "mgm_trade_times",
        title = "分享對象交易次數"
    ), // 分享對象交易次數
    UNKNOWN(
        key = "",
        title = ""
    ); // 未知

    companion object {
        fun find(key: String): MissionTargetType {
            return entries.find { it.key == key } ?: TRADE_AMOUNT
        }

        /**
         * 是否為分享對象(非用戶本人的用戶)
         */
        fun isMgmOthers(targets: List<MissionTarget>) = targets.any {
            it.type == MGM_TRADE_AMT || it.type == MGM_TRADE_TIMES
        }
    }
}