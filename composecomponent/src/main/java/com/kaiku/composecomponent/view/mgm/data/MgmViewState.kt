package com.kaiku.composecomponent.view.mgm.data

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import com.kaiku.composecomponent.color_333333

/**
 * Mgm畫面上由 firebase 去控的字串
 */
data class MgmStringRes(
    val topBarTitleText: String = "推薦好友",
    val promoteTitleText: String = "推薦碼",
    val pocketEventDetailText: String = "活動詳情",
    val shareBtnText: String = "立即分享",
    val twMgmCountText: String = "台股成功分享總數",
    val sbMgmCountText: String = "美股成功分享總數",
    val gotoMissionBtnText: String = "前往口袋任務查看進度"
) {
    companion object {
        val DEFAULT = MgmStringRes()
    }
}

/**
 * Mgm畫面上由 viewModel 控制的資料
 */
data class MgmViewState(
    var topBarBackground: Color = color_333333,
    val bannerUrl: String = "",
    val missionTitle: String = "",
    val missionContent: String = "",
    val code: String = "",
    val qrCode: Bitmap = Bitmap.createBitmap(
        130,
        130,
        Bitmap.Config.ARGB_8888
    ),
    val link: String = "",
    val countTw: Int = 0,
    val countSb: Int = 0
) {
    companion object {
        val PREVIEW = MgmViewState(
            bannerUrl = "https://www.google.com",
            missionTitle = "87月推薦好友任務",
            missionContent = "說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明說明",
            code = "aka5487",
            link = "",
            countTw = 1,
            countSb = 2
        )
    }

}
