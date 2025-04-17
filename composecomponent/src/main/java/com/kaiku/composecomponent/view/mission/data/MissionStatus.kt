package com.kaiku.composecomponent.view.mission.data

enum class MissionStatus(
    val key: String
) {
    ACTIVE(key = "active"), // 進行中
    END(key = "end"), // 已結束
    NOT_START(key = "not_start"), // 未開始
    SUSPEND(key = "suspend"), // 已終止
    UNKNOWN(key = ""); // 未知

    companion object {
        fun find(key: String): MissionStatus {
            return entries.find { it.key == key } ?: UNKNOWN
        }

        fun isMissionEnd(status: String): Boolean {
            return find(status) == END
        }
    }
}