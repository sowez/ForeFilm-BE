package com.sowez.photo.error

enum class ErrorCode(
    val code: Int,
    val message: String
) {
    // SUCCESS
    SUCCESS(200, "OK"),

    // NOT FOUND
    NOT_FOUND(404, "NOT FOUND"),
    STORE_NOT_EXIST(404, "존재하지 않는 매장입니다."),

}