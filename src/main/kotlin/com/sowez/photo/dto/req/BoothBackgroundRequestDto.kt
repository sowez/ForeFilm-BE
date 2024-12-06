package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto

data class BoothBackgroundCreateReqDto(
        val boothBackgroundColorName: String,
        val boothBackgroundColorCode: String
) : SnakeCaseDto()
