package com.kaiku.composecomponent.view.mission.data

enum class RewardStatus(
    val key: String
) {
    HAVE_RECEIVED(key = "HR"), // 已領取
    UNRECEIVED(key = "UR"), // 可領取
    CANT_RECEIVE(key = "CR"), // 不可領取
    UNKNOWN(key = ""); // 未知

    companion object {
        fun find(key: String): RewardStatus {
            return entries.find { it.key == key } ?: UNKNOWN
        }

        fun isAllReceived(rewardStatusList: List<String>): Boolean {
            return rewardStatusList.all { RewardStatus.find(it) == HAVE_RECEIVED }
        }

        fun hasRewardUnReceived(rewardStatusList: List<String>) : Boolean {
            return rewardStatusList.any { RewardStatus.find(it) == UNRECEIVED }
        }
    }
}