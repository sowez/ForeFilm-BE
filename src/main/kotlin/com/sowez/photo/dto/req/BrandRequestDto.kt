package com.sowez.photo.dto.req

import com.sowez.photo.dto.SnakeCaseDto
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BrandCreateReqDto(
        val brandId: Long,
        val brandLogoImageId: Long,
        val brandName: String
) : SnakeCaseDto()

data class BrandEditReqDto(
        val brandId: Long,
        val brandLogoImageId: Long,
        val brandName: String
) : SnakeCaseDto()
