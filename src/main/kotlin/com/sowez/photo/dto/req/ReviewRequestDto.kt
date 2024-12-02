package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.Base64

data class ReviewCreateReqDto(
    @field:NotNull
    val storeId: Long,
    @field:NotBlank
    val reviewNickname: String,
    @field:NotBlank
    val reviewPassword: String,
    @field:NotBlank
    val reviewContents: String,
    val reviewTagIds: List<Long>?,
    val reviewImages: List<String>?
): SnakeCaseDto()
