package com.kaiku.cryptospot.data.remote.dto

import com.kaiku.cryptospot.domain.model.Status

data class StatusDto(
    val credit_count: Int,
    val elapsed: Int,
    val error_code : Int,
    val error_message: String = "",
    val timestamp: String
)


fun StatusDto.toData() : Status {
    return Status(
        errorCode = error_code,
        errorMessage = error_message
    )
}

