package com.sowez.photo.dto.req;

import com.sowez.photo.dto.SnakeCaseDto
import jakarta.validation.constraints.NotBlank

data class TagCreateReqDto (
    @field:NotBlank
    val tagContents: String,
    val tagEmojiName: String
): SnakeCaseDto()
