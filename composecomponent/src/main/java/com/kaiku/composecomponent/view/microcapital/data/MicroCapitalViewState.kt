package com.kaiku.composecomponent.view.microcapital.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.GlobalConstant
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_39b54a
import com.kaiku.composecomponent.color_9e9e9f

/**
 * MicroCapitalViewState 小資方案UI狀態
 *
 * @property stringRes 小資方案文案
 * @property userStatus 用戶狀態
 * @property isReverseAction 是否為非Default狀態 (正在狀態裝換中，正在取消中 || 正在登記中)
 * @property activityStatus 活動狀態
 * @property registrationDate 登記日期
 * @property effectiveDate 生效日期
 * @property twPlatformInfo 台股相關資訊
 * @property usPlatformInfo 美股相關資訊
 */
data class MicroCapitalViewState(
    val stringRes: MicroCapitalStringRes = MicroCapitalStringRes.DEFAULT,
    val userStatus: UserStatus = UserStatus.PARTICIPATE,
    val isReverseAction: Boolean = false,
    val activityStatus: ActivityStatus = ActivityStatus.IN_PROGRESS,
    val registrationDate: String = GlobalConstant.PK_EMPTY_DASH,
    val effectiveDate: String = GlobalConstant.PK_EMPTY_DASH,
    val twPlatformInfo: PlatformInfo = PlatformInfo.EMPTY,
    val usPlatformInfo: PlatformInfo = PlatformInfo.EMPTY
) {
    sealed class UserStatusRes(
        open val stringRes: MicroCapitalStringRes,
        open val description: String,
        open val reverseDescription: String,
        open val reverseBtnText: String,
        @DrawableRes open val resId: Int,
        open val backgroundColor: Color
    ) {

        data class Participate(
            override val stringRes: MicroCapitalStringRes = MicroCapitalStringRes.DEFAULT,
            override val description: String = stringRes.participateDescription,
            override val reverseDescription: String = stringRes.participateReverseDescription,
            override val reverseBtnText: String = stringRes.participateReverseBtnText,
            @DrawableRes override val resId: Int = R.drawable.lib_pocket_ic_micro_capital_participate,
            override val backgroundColor: Color = color_39b54a.copy(alpha = 0.4f)
        ) : UserStatusRes(
            stringRes = stringRes,
            description = description,
            reverseDescription = reverseDescription,
            reverseBtnText = reverseBtnText,
            resId = resId,
            backgroundColor = backgroundColor
        )

        data class Cancel(
            override val stringRes: MicroCapitalStringRes = MicroCapitalStringRes.DEFAULT,
            override val description: String = stringRes.cancelDescription,
            override val reverseDescription: String = stringRes.cancelReverseDescription,
            override val reverseBtnText: String = stringRes.cancelReverseBtnText,
            @DrawableRes override val resId: Int = R.drawable.lib_pocket_ic_micro_capital_cancel,
            override val backgroundColor: Color = color_9e9e9f.copy(alpha = 0.4f)
        ) : UserStatusRes(
            stringRes = stringRes,
            description = description,
            reverseDescription = reverseDescription,
            reverseBtnText = reverseBtnText,
            resId = resId,
            backgroundColor = backgroundColor
        )
    }

    enum class UserStatus {
        PARTICIPATE,
        CANCEL
    }

    enum class ActivityStatus {
        IN_PROGRESS,
        END
    }

    data class PlatformInfo(
        val tradeAmount: String? = null,
        val tradeCount: String? = null,
        val tradeFee: String? = null
    ) {
        companion object {
            val EMPTY = PlatformInfo()
        }
    }

    fun getStatusRes(): UserStatusRes {
        return when (userStatus) {
            UserStatus.PARTICIPATE -> UserStatusRes.Participate(stringRes = stringRes)
            UserStatus.CANCEL -> UserStatusRes.Cancel(stringRes = stringRes)
        }
    }
    
    fun isCancelBtnVisible(): Boolean {
        return userStatus == UserStatus.PARTICIPATE && !isReverseAction ||
                userStatus == UserStatus.CANCEL && isReverseAction
    }

    fun isReRegisterBtnVisible(): Boolean {
        return userStatus == UserStatus.PARTICIPATE
                && isReverseAction
                && activityStatus == ActivityStatus.IN_PROGRESS ||
                userStatus == UserStatus.CANCEL
                && !isReverseAction
                && activityStatus == ActivityStatus.IN_PROGRESS
    }

    fun showMoreInfo(): Boolean {
        return userStatus == UserStatus.PARTICIPATE
                && !isReverseAction
                && activityStatus == ActivityStatus.IN_PROGRESS
    }

    companion object {
        val PREVIEW = MicroCapitalViewState(
            userStatus = UserStatus.PARTICIPATE,
            isReverseAction = false,
            activityStatus = ActivityStatus.IN_PROGRESS
        )
    }
}


