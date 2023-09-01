package com.sowez.photo.error

open class CustomException(
    val errorCode: ErrorCode,
    msg: String = "",
    override val cause: Throwable? = null
): RuntimeException(
    if (msg.isBlank()) errorCode.message else "${errorCode.message} - $msg"
)