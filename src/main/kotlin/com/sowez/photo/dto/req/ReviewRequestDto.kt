package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto
import jakarta.validation.constraints.NotBlank
import java.util.Base64

data class ReviewCreateReqDto(
    @field:NotBlank
    val reviewNickname: String,
    @field:NotBlank
    val reviewPassword: String,
    @field:NotBlank
    val reviewContents: String,
    @field:NotBlank
    val reviewTagIds: List<Long>,
    val reviewImages: List<Base64>
): SnakeCaseDto()
