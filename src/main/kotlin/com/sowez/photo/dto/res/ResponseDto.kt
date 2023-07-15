package com.sowez.photo.dto.res
import com.fasterxml.jackson.annotation.JsonInclude
import com.sowez.photo.error.ErrorCode

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseDto<T>(
    errorCode: ErrorCode = ErrorCode.SUCCESS,
    val body: T? = null
) {
    val status: Int = errorCode.code
    val message: String = errorCode.message
}

data class CreateResponseDto(
    val created: Long
)
