package com.sowez.photo.dto

data class BoothBackgroundInfoResDto(
        val boothBackgroundColorId: Long,
        val boothBackgroundColorName: String,
        val boothBackgroundColorCode: String
): SnakeCaseDto()

data class BoothBackgroundInfosResDto (
        val boothBackgroundColors: List<BoothBackgroundInfoResDto> = mutableListOf()
): SnakeCaseDto()