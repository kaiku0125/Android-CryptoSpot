package com.kaiku.composecomponent.view.mission.data

enum class MissionApportion(val key: String) {

    AUTO("auto"), // 自動發放
    MANUAL("manual"), // 人工發放
    UNKNOWN(""); // 未知

    companion object {
        fun find(key: String): MissionApportion {
            return entries.find { it.key == key } ?: AUTO
        }
    }
}