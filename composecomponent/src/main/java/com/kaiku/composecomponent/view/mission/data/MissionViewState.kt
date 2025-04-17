package com.kaiku.composecomponent.view.mission.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_discount
import com.kaiku.composecomponent.color_discount_bg
import com.kaiku.composecomponent.color_exchange
import com.kaiku.composecomponent.color_exchange_bg
import com.kaiku.composecomponent.color_gift_bg_tw
import com.kaiku.composecomponent.color_raffle
import com.kaiku.composecomponent.color_raffle_bg
import com.kaiku.composecomponent.theme_color_primary_tw
import com.kaiku.composecomponent.theme_color_primary_us
import java.util.UUID

data class MissionViewState(
    val hint: String = "",
    val couponList: List<CouponState> = DEFAULT_COUPON_LIST,
    val missionList: List<MissionCardState> = emptyList(),
    val isShowLoading: Boolean = false
) {

    companion object {
        val DEFAULT_COUPON_LIST = listOf(
            CouponState.DISCOUNT,
            CouponState.RAFFLE,
            CouponState.EXCHANGE
        )

        // 僅作為preview使用
        val PREVIEW_STEP_MISSION_LIST = listOf(
            MissionCardState(
                missionCategory = MissionCategory.STEP,
                dialogImageId = R.drawable.pocket_ic_mission_gift,
                dialogImageBg = color_gift_bg_tw,
                targets = MissionTarget.PREVIEW_STEP,
                rewards = MissionReward.PREVIEW_STEP
            ),
            MissionCardState(
                isTw = false,
                missionCategory = MissionCategory.STEP,
                dialogImageId = R.drawable.pocket_ic_mission_gift,
                dialogImageBg = color_gift_bg_tw,
                targets = MissionTarget.PREVIEW_STEP2,
                rewards = MissionReward.PREVIEW_STEP
            ),
        )

        // 僅作為preview使用
        val PREVIEW_NORMAL_MISSION_LIST = listOf(
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
                dialogImageBg = color_discount_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL_CAN_RECEIVED
            ),
            MissionCardState(
                isTw = false,
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
                dialogImageBg = color_discount_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            ),
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_raffle_coupon,
                dialogImageBg = color_raffle_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            ),
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_raffle_coupon,
                dialogImageBg = color_raffle_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            ),
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_exchange_coupon,
                dialogImageBg = color_exchange_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            ),
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_exchange_coupon,
                dialogImageBg = color_exchange_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            ),
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_exchange_coupon,
                dialogImageBg = color_exchange_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            ),
            MissionCardState(
                missionCategory = MissionCategory.NORMAL,
                dialogImageId = R.drawable.pocket_ic_mission_exchange_coupon,
                dialogImageBg = color_exchange_bg,
                targets = MissionTarget.PREVIEW_NORMAL,
                rewards = MissionReward.PREVIEW_NORMAL
            )
        )

        val PREVIEW_MGM_MISSION_LIST = listOf(
            MissionCardState(
                missionCategory = MissionCategory.MGM,
                dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
                dialogImageBg = color_discount_bg,
                title = "分享好友",
                description = "用戶分享好友達1人，送一份獎勵",
                mgmCount = 10,
                targets = MissionTarget.PREVIEW_MGM,
                rewards = MissionReward.PREVIEW_MGM
            ),
            MissionCardState(
                missionCategory = MissionCategory.MGM,
                dialogImageId = R.drawable.pocket_ic_mission_discount_coupon,
                dialogImageBg = color_discount_bg,
                title = "分享好友",
                description = "用戶分享好友達1人，送一份獎勵",
                mgmCount = 87,
                targets = MissionTarget.PREVIEW_MGM2,
                rewards = MissionReward.PREVIEW_MGM
            ),
        )

        // 僅作為preview使用
        val PREVIEW = MissionViewState(
            hint = "結算提示文字",
            couponList = DEFAULT_COUPON_LIST,
            missionList = PREVIEW_STEP_MISSION_LIST + PREVIEW_MGM_MISSION_LIST + PREVIEW_NORMAL_MISSION_LIST,
        )
    }
}

/**
 * 任務背包item
 *
 * @property type 背包item類型
 * @property imageId 獎勵圖片樣式
 * @property imageColor 獎勵圖片顏色
 * @property bgColor 獎勵背景
 * @property number 獎勵數量
 * @property unit 計量單位
 * @property aniState 動畫設定
 */
data class CouponState(
    val type: String,
    @DrawableRes val imageId: Int,
    val imageColor: Color,
    val bgColor: Color,
    val number: String,
    val unit: String,
    val aniState: CouponAnimationState = CouponAnimationState.DEFAULT
) {
    companion object {

        const val TYPE_DISCOUNT = "1" // 折抵金
        const val TYPE_RAFFLE = "2" // 抽獎券
        const val TYPE_EXCHANGE = "3" // 兌換券
        const val TYPE_UNKNOWN = "unknown" // 未知

        fun String.toCouponUnit(): String = when (this) {
            TYPE_DISCOUNT -> "元"
            TYPE_RAFFLE -> "張"
            TYPE_EXCHANGE -> "張"
            else -> ""
        }

        val DISCOUNT = CouponState(
            type = TYPE_DISCOUNT,
            imageId = R.drawable.pocket_ic_mission_discount_coupon,
            imageColor = color_discount,
            bgColor = color_discount_bg,
            number = "--",
            unit = ""
        )

        val RAFFLE = CouponState(
            type = TYPE_RAFFLE,
            imageId = R.drawable.pocket_ic_mission_raffle_coupon,
            imageColor = color_raffle,
            bgColor = color_raffle_bg,
            number = "--",
            unit = ""
        )

        val EXCHANGE = CouponState(
            type = TYPE_EXCHANGE,
            imageId = R.drawable.pocket_ic_mission_exchange_coupon,
            imageColor = color_exchange,
            bgColor = color_exchange_bg,
            number = "--",
            unit = ""
        )
    }
}

/**
 * 兌換券動畫
 *
 * @property diff 相差數量
 * @property isAniRunning 是否啟動動畫
 */
data class CouponAnimationState(
    val diff: String,
    val isAniRunning: Boolean
) {
    companion object {
        val DEFAULT = CouponAnimationState(
            diff = "",
            isAniRunning = false
        )
    }
}


/**
 * 任務卡片狀態
 *
 * @property isTw 任務屬於哪個平台
 * @property missionId 任務ID
 * @property missionCategory 任務屬性
 * @property dialogImageId 圖片樣式
 * @property dialogImageBg 圖片背景
 * @property couponType 前往我的優惠的參數
 * @property title 任務標題
 * @property description 任務說明
 * @property rewardDescription 獎勵發放敘述
 * @property validTime 任務有效時間
 * @property missionStatus 任務狀態
 * @property mgmCount 推薦人數
 * @property targets 任務獎勵
 * @property rewards 任務發放時間
 * @property mascotUrl 吉祥物圖片url
 * @property isRewardReachable 任務獎勵是否可領取
 * @property isShowDim 是否顯示黑化遮罩
 * @property isMissionEnd 後端任務狀態是否結束
 * @property lastApportionTime 是否有最後發放日
 * @property isExpanded 任務卡片是否展開
 * @property isVisible 任務卡片是否顯示過
 */
data class MissionCardState(
    val isTw: Boolean = true,
    val missionId: String = UUID.randomUUID().toString(),
    val missionCategory: MissionCategory = MissionCategory.UNKNOWN,
    val imageUrl: String = "",
    @DrawableRes val dialogImageId: Int = R.drawable.pocket_ic_mission_exchange_coupon,
    val dialogImageBg: Color = color_exchange_bg,
    val couponType: String = CouponState.TYPE_UNKNOWN,
    val title: String = "任務標題任務標題任務標題",
    val description: String = "任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務說明任務",
    val rewardDescription: String = "獎勵為即時發放",
    val validTime: String = "2024/02/01~2024/02/28",
    val missionStatus: MissionStatus = MissionStatus.UNKNOWN,
    val mgmCount: Int? = null,
    val targets: List<MissionTarget> = emptyList(),
    val rewards: List<MissionReward> = emptyList(),
    val mascotUrl: String = "",
    val isRewardReachable: Boolean = true,
    val isShowDim: Boolean = false,
    val isMissionEnd: Boolean = false,
    val lastApportionTime: String? = null,
    val isExpanded: Boolean = true,
    val isVisible: Boolean = false
) {

    fun isStepMission() = missionCategory == MissionCategory.STEP

    fun getPrimaryColor(): Color = if (isTw) {
        theme_color_primary_tw
    } else {
        theme_color_primary_us
    }
}

// 目標object
data class MissionTarget(
    val type: MissionTargetType = MissionTargetType.UNKNOWN,
    val nowStep: Int = 0,
    val totalStep: Int = 100,
    val limitMax: Int? = null // 若為空則為無限
) {
    fun getPercentage(): Float = nowStep.toFloat() / totalStep

    companion object {
        // 僅作為preview使用
        val PREVIEW_NORMAL = listOf(
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 5000,
                totalStep = 100000,
            ),
            MissionTarget(
                type = MissionTargetType.TRADE_TIMES,
                nowStep = 2,
                totalStep = 10,
            ),
        )

        // 僅作為preview使用
        val PREVIEW_STEP = listOf(
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 50000,
                totalStep = 100000,
            )
        )

        // 僅作為preview使用
        val PREVIEW_STEP2 = listOf(
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 9000,
                totalStep = 10000,
            ),
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 9000,
                totalStep = 30000,
            ),
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 9000,
                totalStep = 50000,
            ),
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 9000,
                totalStep = 70000,
            ),
            MissionTarget(
                type = MissionTargetType.TRADE_AMOUNT,
                nowStep = 9000,
                totalStep = 100000,
            )
        )

        val PREVIEW_MGM = listOf(
            MissionTarget(
                type = MissionTargetType.RECOMMEND_COUNT,
                nowStep = 1,
                totalStep = 5,
                limitMax = 999
            )
        )

        val PREVIEW_MGM2 = listOf(
            MissionTarget(
                type = MissionTargetType.MGM_TRADE_AMT,
                nowStep = 100,
                totalStep = 10000,
                limitMax = null
            ),
            MissionTarget(
                type = MissionTargetType.MGM_TRADE_TIMES,
                nowStep = 2,
                totalStep = 5,
                limitMax = null
            ),
        )
    }
}

// 獎勵Object
data class MissionReward(
    val rewardId: String = UUID.randomUUID().toString(),
    val type: String = "",
    val step: String? = null, //階段任務才有
    val name: String = "",
    val number: Int = 0,
    val unit: String = "張",
    val status: RewardStatus = RewardStatus.UNKNOWN,
    val apportion: RewardApportion? = null,
    val achieveCount: Int? = null, // 累積達成數
    val totalAchieveCount: Int? = null, // 總達成數
    val receiveReward: Int? = null // 已領獎勵
) {
    fun String.toTypeName(): String {
        return when (this) {
            CouponState.TYPE_DISCOUNT -> "折抵金"
            CouponState.TYPE_RAFFLE -> "抽獎券"
            CouponState.TYPE_EXCHANGE -> "兌換券"
            else -> ""
        }
    }

    companion object {

        // 僅作為preview使用
        val PREVIEW_NORMAL = listOf(
            MissionReward(
                type = CouponState.TYPE_EXCHANGE,
                name = "新阿姆斯特朗炫風噴射阿姆斯特朗砲新阿姆斯特朗炫風噴射阿姆斯特朗砲",
                number = 1,
                unit = "張"
            ),
            MissionReward(
                type = CouponState.TYPE_EXCHANGE,
                name = "新阿姆斯特朗炫風噴射阿姆斯特朗砲新阿姆斯特朗炫風噴射阿姆斯特朗砲",
                number = 1,
                unit = "張"
            )
        )

        // 僅作為preview使用
        val PREVIEW_NORMAL_CAN_RECEIVED = listOf(
            MissionReward(
                rewardId = "1",
                type = CouponState.TYPE_EXCHANGE,
                name = "新阿姆斯特朗炫風噴射阿姆斯特朗砲新阿姆斯特朗炫風噴射阿姆斯特朗砲",
                number = 1,
                unit = "枚",
                status = RewardStatus.UNRECEIVED
            ),
            MissionReward(
                rewardId = "2",
                type = CouponState.TYPE_EXCHANGE,
                name = "比特幣",
                number = 2,
                unit = "顆",
                status = RewardStatus.UNRECEIVED
            )
        )

        // 僅作為preview使用
        val PREVIEW_DIALOG = MissionReward(
            type = CouponState.TYPE_EXCHANGE,
            name = "新阿姆斯特朗炫風噴射阿姆斯特朗砲新阿姆斯特朗炫風噴射阿姆斯特朗砲",
            number = 1,
            unit = "枚"
        )

        // 僅作為preview使用
        val PREVIEW_STEP = listOf(
            MissionReward(
                type = CouponState.TYPE_EXCHANGE,
                step = "10000",
                name = "星巴克 兌換券",
                number = 1,
                unit = "張",
                status = RewardStatus.HAVE_RECEIVED
            ),
            MissionReward(
                rewardId = "1",
                type = CouponState.TYPE_EXCHANGE,
                step = "30000",
                name = "星巴克 兌換券",
                number = 1,
                unit = "張",
                status = RewardStatus.UNRECEIVED
            ),
            MissionReward(
                rewardId = "2",
                type = CouponState.TYPE_RAFFLE,
                step = "50000",
                name = "星巴克 抽獎券",
                number = 2,
                unit = "張",
                status = RewardStatus.UNRECEIVED
            ),
            MissionReward(
                type = CouponState.TYPE_RAFFLE,
                step = "76540",
                name = "星巴克 抽獎券",
                number = 4,
                unit = "張"
            ),
            MissionReward(
                type = CouponState.TYPE_DISCOUNT,
                step = "987654321",
                name = "口袋折抵金",
                number = 5487,
                unit = "元"
            )
        )

        val PREVIEW_MGM = listOf(
            MissionReward(
                type = CouponState.TYPE_EXCHANGE,
                name = "比特幣",
                number = 1,
                unit = "顆",
                status = RewardStatus.UNRECEIVED,
                achieveCount = 3,
                totalAchieveCount = 5,
                receiveReward = 3
            ),
            MissionReward(
                type = CouponState.TYPE_EXCHANGE,
                name = "新阿姆斯特朗炫風噴射阿姆斯特朗砲",
                number = 2,
                unit = "枚",
                status = RewardStatus.UNRECEIVED,
                achieveCount = 3,
                totalAchieveCount = 6,
                receiveReward = 3
            )
        )
    }
}

// 發放方式Object
data class RewardApportion(
    val code: MissionApportion = MissionApportion.UNKNOWN,
    val apportionDate: String = "2222/22/22"
)

