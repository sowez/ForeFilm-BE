package com.sowez.photo.dto.req;

import com.sowez.photo.dto.SnakeCaseDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TagCreateReqDto (
    @field:NotNull
    val tagContents: String,
    @field:NotNull
    val tagEmojiName: String
): SnakeCaseDto()
