package com.kaiku.composecomponent.view.accountinfo.data

data class AccountInfoPlatformState(
    val isShowPlatform: Boolean,
    val platformName: String,
    val accountType: String,
    val accountName: String,
    val accountNumber: String
) {
    companion object {

        val PREVIEW = AccountInfoPlatformState(
            isShowPlatform = true,
            platformName = "台股",
            accountType = "台幣交割",
            accountName = "彰化囝仔",
            accountNumber = "6620-54879487"
        )

        val INIT = AccountInfoPlatformState(
            isShowPlatform = true,
            platformName = "",
            accountType = "",
            accountName = "",
            accountNumber = ""
        )
    }
}
