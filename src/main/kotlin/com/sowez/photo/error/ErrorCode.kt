package com.sowez.photo.error

enum class ErrorCode(
    val code: Int,
    val message: String
) {
    // SUCCESS
    SUCCESS(200, "OK"),

    // NOT FOUND
    NOT_FOUND(404, "NOT FOUND"),

}