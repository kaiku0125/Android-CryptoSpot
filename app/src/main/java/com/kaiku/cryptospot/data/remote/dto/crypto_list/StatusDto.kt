package com.kaiku.cryptospot.data.remote.dto.crypto_list

import com.kaiku.cryptospot.domain.model.Status

data class StatusDto(
    val credit_count: Int,
    val elapsed: Int,
    val error_code : Int,
    val error_message: String = "",
    val timestamp: String
)


fun StatusDto.toPoint() : Status {
    return Status(
        errorCode = error_code,
        errorMessage = error_message
    )
}

