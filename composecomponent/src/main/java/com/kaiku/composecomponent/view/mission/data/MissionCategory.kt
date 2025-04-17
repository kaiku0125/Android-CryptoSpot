package com.kaiku.composecomponent.view.mission.data

enum class MissionCategory(val key: Int) {

    NORMAL(1), // 一般任務
    STEP(2), // 階段任務
    MGM(3), // 分享好友任務
    UNKNOWN(0); // 未知

    companion object {
        fun find(key: Int): MissionCategory {
            return entries.find { it.key == key } ?: UNKNOWN
        }
    }
}