package com.sowez.photo.dto.res
import com.sowez.photo.error.ErrorCode

class ResponseDto<T>(
    val body: T,
    errorCode: ErrorCode = ErrorCode.SUCCESS
) {
    val status: Int = errorCode.code
    val message: String = errorCode.message
}

data class CreateResponseDto(
    val created: Long
)
