package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto

data class BoothBackgroundCreateReqDto(
        val boothBackgroundColorId: Int,
        val boothBackgroundColorName: String,
        val boothBackgroundColorCode: String
) : SnakeCaseDto()
