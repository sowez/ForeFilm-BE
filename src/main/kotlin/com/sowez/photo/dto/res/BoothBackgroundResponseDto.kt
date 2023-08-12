package com.sowez.photo.dto

data class BoothBackgroundInfoResDto (
        val boothBackgroundColorId: Int,
        val boothBackgroundColorName: String,
        val boothBackgroundColorCode: String
): SnakeCaseDto()